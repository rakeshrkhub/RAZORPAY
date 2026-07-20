package com.rakesh.razorpay.payment.service;

import com.rakesh.razorpay.payment.dto.request.CreateOrderRequest;
import com.rakesh.razorpay.payment.dto.response.PaymentResponse;
import com.rakesh.razorpay.payment.dto.response.OrderResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponse createOrder(UUID merchantId, CreateOrderRequest createOrderRequest);

    OrderResponse getById(UUID merchantId, UUID orderId);

    OrderResponse cancelOrder(UUID merchantId, UUID orderId);

    List<PaymentResponse> getPayments(UUID merchantId, UUID orderId);
}
