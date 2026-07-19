package com.rakesh.razorpay.payment.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rakesh.razorpay.common.entity.Money;
import com.rakesh.razorpay.common.enums.PaymentMethods;
import com.rakesh.razorpay.common.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaymentResponse (
        UUID id,
        UUID orderId,
        UUID merchantId,
        Money amount,
        PaymentStatus status,
        PaymentMethods paymentMethod,
        Map<String, Object> methodDetails,
        String cardLastFour,
        String cardBrand,
        String bankReference,
        String errorCode,
        String errorDescription,
        Long refundedAmountPaise,
        LocalDateTime captureAt,
        LocalDateTime createdAt
){
}
