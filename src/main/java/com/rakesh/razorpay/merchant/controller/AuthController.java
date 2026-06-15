package com.rakesh.razorpay.merchant.controller;

import com.rakesh.razorpay.merchant.dto.request.MerchantSignUpRequest;
import com.rakesh.razorpay.merchant.dto.response.MerchantResponse;
import com.rakesh.razorpay.merchant.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MerchantResponse> signUp(@RequestBody @Valid MerchantSignUpRequest signUpRequest){
        return ResponseEntity.status(HttpStatus.CREATED).
                body(authService.signUp(signUpRequest));
    }
}
