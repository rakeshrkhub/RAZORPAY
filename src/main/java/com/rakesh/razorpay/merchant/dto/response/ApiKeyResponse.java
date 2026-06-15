package com.rakesh.razorpay.merchant.dto.response;

import com.rakesh.razorpay.common.enums.Environment;

import java.util.UUID;

public record ApiKeyResponse(
        UUID id,
        String keyId,
        String keySecret,
        Environment environment
) {
}
