package com.rakesh.razorpay.merchant.service.impl;

import com.rakesh.razorpay.common.exceptions.ResourceNotFoundException;
import com.rakesh.razorpay.common.util.RandomizerUtil;
import com.rakesh.razorpay.merchant.dto.request.ApiKeyRequest;
import com.rakesh.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.rakesh.razorpay.merchant.dto.response.ApiKeyResponse;
import com.rakesh.razorpay.merchant.entity.ApiKey;
import com.rakesh.razorpay.merchant.entity.Merchant;
import com.rakesh.razorpay.merchant.repository.ApiKeyRepository;
import com.rakesh.razorpay.merchant.repository.MerchantRepository;
import com.rakesh.razorpay.merchant.service.ApiKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ApiKeyServiceImpl implements ApiKeyService {
    private final ApiKeyRepository apiKeyRepository;
    private final MerchantRepository merchantRepository;

    @Override
    public ApiKeyCreateResponse create(UUID merchantId, ApiKeyRequest apiKeyRequest) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("MERCHANT_NOT_FOUND", "Merchant not found"));

        String keyId = "rzp_"+apiKeyRequest.environment().name().toLowerCase()+ RandomizerUtil.generateRandomString(24);
        String rawSecret = RandomizerUtil.generateRandomString(32);
        ApiKey apiKey = ApiKey.builder()
                .keyId(keyId)
                .keySecretHash(rawSecret) //TODO: encode the rawSecret with BcryptPasswordEncoder before saving to DB
                .merchant(merchant)
                .environment(apiKeyRequest.environment())
                .build();
        apiKeyRepository.save(apiKey);
        return new ApiKeyCreateResponse(apiKey.getId(), apiKey.getKeyId(), rawSecret, apiKey.getEnvironment());
    }

    @Override
    public List<ApiKeyResponse> getApiKeysByMerchantId(UUID merchantId) {
        return apiKeyRepository.findByMerchant_Id(merchantId)
                .stream()
                .map(apiKey ->
                        new ApiKeyResponse(
                                apiKey.getId(),
                                apiKey.getKeyId(),
                                apiKey.getEnvironment(),
                                apiKey.isEnabled(),
                                apiKey.getLastUsedAt(),
                                null))
                .toList();
    }

    @Override
    public void revoke(UUID merchantId, UUID keyId) {
        ApiKey apiKey = apiKeyRepository.findById(keyId)
                .filter(key -> key.getMerchant().getId().equals(merchantId))
                .orElseThrow(() -> new ResourceNotFoundException("API_KEY_NOT_FOUND", "API Key not found with keyId: " + keyId + " for merchantId: " + merchantId));
        apiKey.setEnabled(false);
        apiKeyRepository.save(apiKey);
    }

    @Override
    public ApiKeyCreateResponse rotate(UUID merchantId, UUID keyId) {
        ApiKey apiKey = apiKeyRepository.findById(keyId)
                .filter(key -> key.getMerchant().getId().equals(merchantId))
                .orElseThrow(() -> new ResourceNotFoundException("API_KEY_NOT_FOUND", "API Key not found with keyId: " + keyId + " for merchantId: " + merchantId));
        apiKey.setPreviousKeySecretHash(apiKey.getKeySecretHash());
        String newRawSecret = RandomizerUtil.generateRandomString(32);
        apiKey.setKeySecretHash(newRawSecret); //TODO: encode the newRawSecret with BcryptPasswordEncoder before saving to DB
        apiKey.setRotatedAt(LocalDateTime.now());
        apiKey.setGracePeriodEndAt(LocalDateTime.now().plusHours(24)); // Set grace period of 24 hours
        apiKeyRepository.save(apiKey);
        return new ApiKeyCreateResponse(apiKey.getId(), apiKey.getKeyId(), newRawSecret, apiKey.getEnvironment());
    }
}
