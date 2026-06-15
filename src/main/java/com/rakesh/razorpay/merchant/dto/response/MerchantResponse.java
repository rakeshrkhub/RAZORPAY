package com.rakesh.razorpay.merchant.dto.response;

import com.rakesh.razorpay.common.enums.BusinessType;
import com.rakesh.razorpay.common.enums.MerchantStatus;
import java.util.UUID;

public record MerchantResponse(
        UUID id,
        String name,
        String email,
        String businessName,
        BusinessType businessType,
        MerchantStatus status
) {
}
