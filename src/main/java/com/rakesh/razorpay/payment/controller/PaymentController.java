package com.rakesh.razorpay.payment.controller;

import com.rakesh.razorpay.payment.dto.request.PaymentInitRequest;
import com.rakesh.razorpay.payment.dto.response.PaymentResponse;
import com.rakesh.razorpay.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    //TODO: replace it with MerchantContext
    UUID merchantId= UUID.fromString("3d5743bb-87e6-4e95-808a-cf094164364d");

    @PostMapping
    public ResponseEntity<PaymentResponse> initiatePayment(@Valid @RequestBody PaymentInitRequest request) {
        PaymentResponse response = paymentService.initiate(merchantId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);

    }
}
