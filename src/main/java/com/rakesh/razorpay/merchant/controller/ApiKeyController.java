package com.rakesh.razorpay.merchant.controller;

import com.rakesh.razorpay.merchant.dto.request.ApiKeyRequest;
import com.rakesh.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.rakesh.razorpay.merchant.dto.response.ApiKeyResponse;
import com.rakesh.razorpay.merchant.service.ApiKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/merchants/{merchantId}/api-keys")
public class ApiKeyController {
    private final ApiKeyService apiKeyService;

    @PostMapping()
    public ResponseEntity<ApiKeyCreateResponse> create(@PathVariable UUID merchantId,
                                                       @Valid @RequestBody ApiKeyRequest apiKeyRequest) {
        ApiKeyCreateResponse apiKeyResponse = apiKeyService.create(merchantId, apiKeyRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(apiKeyResponse);
    }

    @GetMapping
    public ResponseEntity<List<ApiKeyResponse>> getApiKeys(@PathVariable UUID merchantId) {
        return ResponseEntity.ok(apiKeyService.getApiKeysByMerchantId(merchantId));
    }

    @DeleteMapping("/{keyId}")
    public ResponseEntity<Void> revoke(@PathVariable UUID merchantId, @PathVariable UUID keyId){
        apiKeyService.revoke(merchantId,keyId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{keyId}/rotate")
    public ResponseEntity<ApiKeyCreateResponse> rotateApiKey(@PathVariable UUID merchantId, @PathVariable UUID keyId){
        ApiKeyCreateResponse apiKeyCreateResponse = apiKeyService.rotate(merchantId,keyId);
        return ResponseEntity.ok(apiKeyCreateResponse);
    }
}
