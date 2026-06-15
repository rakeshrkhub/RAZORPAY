package com.rakesh.razorpay.merchant.service;

import com.rakesh.razorpay.merchant.dto.request.MerchantSignUpRequest;
import com.rakesh.razorpay.merchant.dto.response.MerchantResponse;

public interface AuthService {
    MerchantResponse signUp(MerchantSignUpRequest signUpRequest);
}
