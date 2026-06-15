package com.rakesh.razorpay.merchant.dto.request;

import com.rakesh.razorpay.common.enums.Environment;

public record ApiKeyRequest(
        Environment environment
) {
}
