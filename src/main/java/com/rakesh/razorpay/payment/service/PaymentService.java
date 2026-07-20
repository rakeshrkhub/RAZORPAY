package com.rakesh.razorpay.payment.service;

import com.rakesh.razorpay.payment.dto.request.PaymentInitRequest;
import com.rakesh.razorpay.payment.dto.response.PaymentResponse;

import java.util.UUID;

public interface PaymentService {
    PaymentResponse initiate(UUID merchantId, PaymentInitRequest request);
}
