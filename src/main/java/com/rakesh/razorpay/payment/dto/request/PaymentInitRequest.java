package com.rakesh.razorpay.payment.dto.request;

import com.rakesh.razorpay.common.enums.PaymentMethods;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.UUID;

public record PaymentInitRequest(

        @NotNull(message = "Order ID is required")
        UUID orderId,

        @NotNull(message = "Payment method is required")
        PaymentMethods methods,

        Map<String, Object> methodDetails
) {
}
