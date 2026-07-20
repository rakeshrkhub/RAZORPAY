package com.rakesh.razorpay.payment.service.impl;

import com.rakesh.razorpay.common.enums.OrderStatus;
import com.rakesh.razorpay.common.enums.PaymentStatus;
import com.rakesh.razorpay.common.exceptions.BusinessRuleViolationException;
import com.rakesh.razorpay.common.exceptions.ResourceNotFoundException;
import com.rakesh.razorpay.payment.dto.request.PaymentInitRequest;
import com.rakesh.razorpay.payment.dto.response.PaymentResponse;
import com.rakesh.razorpay.payment.entity.OrderRecord;
import com.rakesh.razorpay.payment.entity.Payment;
import com.rakesh.razorpay.payment.gateway.PaymentGatewayRouter;
import com.rakesh.razorpay.payment.gateway.dto.PaymentRequest;
import com.rakesh.razorpay.payment.gateway.dto.PaymentResult;
import com.rakesh.razorpay.payment.mapper.PaymentMapper;
import com.rakesh.razorpay.payment.repository.OrderRepository;
import com.rakesh.razorpay.payment.repository.PaymentRepository;
import com.rakesh.razorpay.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayRouter paymentGatewayRouter;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentResponse initiate(UUID merchantId, PaymentInitRequest request) {
        OrderRecord order = orderRepository.findByIdAndMerchantId(request.orderId(), merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("ORDER_NOT_FOUND", "Order not found for ID: " + request.orderId() + " and Merchant ID: " + merchantId));

        if(order.getOrderStatus() != OrderStatus.CREATED && order.getOrderStatus() != OrderStatus.PAID){
            throw new BusinessRuleViolationException("INVALID_ORDER_STATUS", "Order status must be either CREATED or PAID to initiate payment. Current status: " + order.getOrderStatus());
        }

        order.setOrderStatus(OrderStatus.ATTEMPTED);
        order.setAttempts(order.getAttempts() + 1);
        orderRepository.save(order);

        Payment payment = Payment.builder()
                .order(order)
                .merchantId(merchantId)
                .amount(order.getAmount())
                .status(PaymentStatus.CREATED)
                .methods(request.methods())
                .methodDetails(request.methodDetails())
                .build();
        paymentRepository.save(payment);

        PaymentRequest paymentRequest = new PaymentRequest(
                payment.getId(),
                request.orderId(),
                merchantId,
                order.getAmount(),
                request.methods(),
                request.methodDetails()
        );

        PaymentResult paymentResult = paymentGatewayRouter.initiatePayment(paymentRequest);
        switch (paymentResult) {
            case PaymentResult.Pending pending -> payment.setProcessorReference(pending.registrationReference());
            case PaymentResult.Failed failed -> {
                payment.setStatus(PaymentStatus.FAILED);
                payment.setError(failed.errorCode());
                payment.setErrorDescription(failed.errorDescription());
            }
        }
        paymentRepository.save(payment);
        return paymentMapper.toPaymentResponse(payment);
    }
}
