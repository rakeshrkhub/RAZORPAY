package com.rakesh.razorpay.merchant.mapper;

import com.rakesh.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.rakesh.razorpay.merchant.dto.response.ApiKeyResponse;
import com.rakesh.razorpay.merchant.entity.ApiKey;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiKeyMapper {
    ApiKeyCreateResponse toApiKeyCreateResponse(ApiKey apiKey);
    List<ApiKeyResponse> toApiKeyResponseList(List<ApiKey> apiKeys);
}
