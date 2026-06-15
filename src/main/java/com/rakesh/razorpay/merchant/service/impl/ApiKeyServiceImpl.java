package com.rakesh.razorpay.merchant.service.impl;

import com.rakesh.razorpay.common.exceptions.ResourceNotFoundException;
import com.rakesh.razorpay.merchant.dto.request.ApiKeyRequest;
import com.rakesh.razorpay.merchant.dto.response.ApiKeyResponse;
import com.rakesh.razorpay.merchant.entity.ApiKey;
import com.rakesh.razorpay.merchant.entity.Merchant;
import com.rakesh.razorpay.merchant.repository.ApiKeyRepository;
import com.rakesh.razorpay.merchant.repository.MerchantRepository;
import com.rakesh.razorpay.merchant.service.ApiKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiKeyServiceImpl implements ApiKeyService {
    private final ApiKeyRepository apiKeyRepository;
    private final MerchantRepository merchantRepository;

    @Override
    public ApiKeyResponse create(UUID merchantId, ApiKeyRequest apiKeyRequest) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("MERCHANT_NOT_FOUND", "Merchant not found"));
        //TODO: keyId and rawSecret generation technique to be change
        String keyId = "rzp_"+apiKeyRequest.environment().name().toUpperCase()+UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        String rawSecret = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        ApiKey apiKey = ApiKey.builder()
                .keyId(keyId)
                .keySecretHash(rawSecret)
                .environment(apiKeyRequest.environment())
                .build();
        apiKeyRepository.save(apiKey);
        return new ApiKeyResponse(apiKey.getId(), apiKey.getKeyId(), rawSecret, apiKey.getEnvironment());
    }
}
