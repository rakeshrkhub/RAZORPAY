package com.rakesh.razorpay.merchant.mapper;

import com.rakesh.razorpay.merchant.dto.request.MerchantSignUpRequest;
import com.rakesh.razorpay.merchant.dto.response.MerchantResponse;
import com.rakesh.razorpay.merchant.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MerchantMapper {
    Merchant toMerchantFromSignUpRequest(MerchantSignUpRequest merchantSignUpRequest);
    MerchantResponse toMerchantResponse(Merchant merchant);
}
