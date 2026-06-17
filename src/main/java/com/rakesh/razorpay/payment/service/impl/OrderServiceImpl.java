package com.rakesh.razorpay.payment.service.impl;

import com.rakesh.razorpay.common.enums.OrderStatus;
import com.rakesh.razorpay.common.exceptions.DuplicateResourceException;
import com.rakesh.razorpay.payment.dto.request.CreateOrderRequest;
import com.rakesh.razorpay.payment.dto.response.OrderResponse;
import com.rakesh.razorpay.payment.entity.OrderRecord;
import com.rakesh.razorpay.payment.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Value("${payment.order.default-expiry-minutes:15}")
    private int defaultOrderExpiryMinutes;

    @Override
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
        return new OrderResponse(
                orderRecord.getId(),
                orderRecord.getMerchantId(),
                orderRecord.getReceipt(),
                orderRecord.getAmount(),
                orderRecord.getOrderStatus(),
                orderRecord.getAttempts(),
                orderRecord.getNotes(),
                null,
                orderRecord.getExpiresAt());
    }
}
