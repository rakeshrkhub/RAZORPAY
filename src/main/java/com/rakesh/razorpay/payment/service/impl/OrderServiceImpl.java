package com.rakesh.razorpay.payment.service.impl;

import com.rakesh.razorpay.common.enums.OrderStatus;
import com.rakesh.razorpay.common.exceptions.BusinessRuleViolationException;
import com.rakesh.razorpay.common.exceptions.DuplicateResourceException;
import com.rakesh.razorpay.common.exceptions.ResourceNotFoundException;
import com.rakesh.razorpay.payment.dto.request.CreateOrderRequest;
import com.rakesh.razorpay.payment.dto.response.PaymentResponse;
import com.rakesh.razorpay.payment.dto.response.OrderResponse;
import com.rakesh.razorpay.payment.entity.OrderRecord;
import com.rakesh.razorpay.payment.entity.Payment;
import com.rakesh.razorpay.payment.mapper.OrderMapper;
import com.rakesh.razorpay.payment.mapper.PaymentMapper;
import com.rakesh.razorpay.payment.repository.OrderRepository;
import com.rakesh.razorpay.payment.repository.PaymentRepository;
import com.rakesh.razorpay.payment.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderMapper orderMapper;

    @Value("${payment.order.default-expiry-minutes:15}")
    private int defaultOrderExpiryMinutes;

    @Override
    @Transactional
    public OrderResponse createOrder(UUID merchantId, CreateOrderRequest createOrderRequest) {
        if(createOrderRequest.receipt()!=null && orderRepository.existsByMerchantIdAndReceipt(merchantId,createOrderRequest.receipt())){
            throw new DuplicateResourceException("DUPLICATE_ORDER_RECEIPT","Order with receipt " + createOrderRequest.receipt() + " already exists for this merchant");
        }
        OrderRecord orderRecord = OrderRecord.builder()
                .merchantId(merchantId)
                .amount(createOrderRequest.amount())
                .notes(createOrderRequest.notes())
                .receipt(createOrderRequest.receipt())
                .orderStatus(OrderStatus.CREATED)
                .expiresAt(createOrderRequest.expiresAt() !=null ? createOrderRequest.expiresAt() : LocalDateTime.now().plusMinutes(defaultOrderExpiryMinutes))
                .build();
        orderRepository.save(orderRecord);
        //TODO:     publish Kafka event about order created
        return orderMapper.toOrderResponse(orderRecord);
    }

    @Override
    public OrderResponse getById(UUID merchantId, UUID orderId) {
        OrderRecord order= orderRepository.findByIdAndMerchantId(orderId,merchantId)
                .orElseThrow(()-> new ResourceNotFoundException("ORDER_NOT_FOUND","Order not found for merchantId: " + merchantId + " and orderId: " + orderId));
        return orderMapper.toOrderResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(UUID merchantId, UUID orderId) {
        OrderRecord order= orderRepository.findByIdAndMerchantId(orderId,merchantId)
                .orElseThrow(()-> new ResourceNotFoundException("ORDER_NOT_FOUND","Order not found for merchantId: " + merchantId + " and orderId: " + orderId));
        if(order.getOrderStatus()==OrderStatus.CANCELLED || order.getOrderStatus()==OrderStatus.PAID){
            throw new BusinessRuleViolationException("ORDER_CANNOT_BE_CANCELLED", "Cannot cancel this order with Order status " + order.getOrderStatus().name());
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        return orderMapper.toOrderResponse(order);
    }

    @Override
    public List<PaymentResponse> getPayments(UUID merchantId, UUID orderId) {
        OrderRecord order= orderRepository.findByIdAndMerchantId(orderId,merchantId)
                .orElseThrow(()-> new ResourceNotFoundException("ORDER_NOT_FOUND","Order not found for merchantId: " + merchantId + " and orderId: " + orderId));
        List<Payment> payments = paymentRepository.findByOrder_Id(order);
        return paymentMapper.toPaymentResponses(payments);
    }
}
