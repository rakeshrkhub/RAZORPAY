package com.rakesh.razorpay.payment.gateway;

import com.rakesh.razorpay.payment.gateway.dto.PaymentRequest;
import com.rakesh.razorpay.payment.gateway.dto.PaymentResult;

public interface PaymentAdapter {
    public PaymentResult initiatePayment(PaymentRequest paymentRequest);
}
