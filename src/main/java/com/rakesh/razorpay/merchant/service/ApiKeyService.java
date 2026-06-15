package com.rakesh.razorpay.merchant.service;

import com.rakesh.razorpay.merchant.dto.request.ApiKeyRequest;
import com.rakesh.razorpay.merchant.dto.response.ApiKeyResponse;

import java.util.UUID;

public interface ApiKeyService {
    ApiKeyResponse create(UUID merchantId, ApiKeyRequest apiKeyRequest);
}
