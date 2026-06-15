package com.rakesh.razorpay.merchant.dto.request;

import com.rakesh.razorpay.common.enums.BusinessType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MerchantSignUpRequest(
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    String name,

    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be null")
    @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters")
    String email,

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    String password,

    @Size(max = 100, message = "Business name must be at most 100 characters")
    String businessName,

    BusinessType businessType) {
}
