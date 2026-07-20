package com.rakesh.razorpay.payment.processor.dto;

import com.rakesh.razorpay.common.entity.Money;
import com.rakesh.razorpay.common.enums.PaymentMethods;

import java.util.Map;

public record PaymentProcessorRequest(
        PaymentMethods methods,
        Money amount,
        Map<String, Object> methodDetails
) {
}
