package com.rakesh.razorpay.payment.dto.response;

import com.rakesh.razorpay.common.entity.Money;
import com.rakesh.razorpay.common.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID merchantId,
        String receipt, //orderId or Merchant Reference Number
        Money amount,
        OrderStatus status,
        Integer attempts,
        Map<String, Object> notes,
        LocalDateTime createdAt,
        LocalDateTime expiresAt
) {
}
