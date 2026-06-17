package com.rakesh.razorpay.payment.service.impl;

import com.rakesh.razorpay.payment.dto.request.CreateOrderRequest;
import com.rakesh.razorpay.payment.dto.response.OrderResponse;

import java.util.UUID;

public interface OrderService {
    OrderResponse createOrder(UUID merchantId, CreateOrderRequest createOrderRequest);
}
