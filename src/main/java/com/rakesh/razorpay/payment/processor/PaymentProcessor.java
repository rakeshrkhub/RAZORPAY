package com.rakesh.razorpay.payment.processor;

import com.rakesh.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.rakesh.razorpay.payment.processor.dto.PaymentProcessorResponse;

public interface PaymentProcessor {
    PaymentProcessorResponse charge(PaymentProcessorRequest request);
}
