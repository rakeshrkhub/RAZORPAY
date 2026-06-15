package com.rakesh.razorpay.merchant.controller;

import com.rakesh.razorpay.merchant.dto.request.ApiKeyRequest;
import com.rakesh.razorpay.merchant.dto.response.ApiKeyResponse;
import com.rakesh.razorpay.merchant.service.ApiKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/merchants/{merchantId}/api-keys")
public class ApiKeyController {
    private final ApiKeyService apiKeyService;

    @PostMapping("/")
    public ResponseEntity<ApiKeyResponse> create(@PathVariable UUID merchantId,
                                                 @Valid @RequestBody ApiKeyRequest apiKeyRequest) {
        ApiKeyResponse apiKeyResponse = apiKeyService.create(merchantId, apiKeyRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(apiKeyResponse);
    }
}
