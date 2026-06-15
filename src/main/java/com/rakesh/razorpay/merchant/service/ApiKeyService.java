package com.rakesh.razorpay.merchant.service;

import com.rakesh.razorpay.merchant.dto.request.ApiKeyRequest;
import com.rakesh.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.rakesh.razorpay.merchant.dto.response.ApiKeyResponse;

import java.util.List;
import java.util.UUID;

public interface ApiKeyService {
    ApiKeyCreateResponse create(UUID merchantId, ApiKeyRequest apiKeyRequest);

    List<ApiKeyResponse> getApiKeysByMerchantId(UUID merchantId);

    void revoke(UUID merchantId, UUID keyId);

    ApiKeyCreateResponse rotate(UUID merchantId, UUID keyId);
}
