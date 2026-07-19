package com.rakesh.razorpay.payment.controller;

import com.rakesh.razorpay.payment.dto.request.CreateOrderRequest;
import com.rakesh.razorpay.payment.dto.response.OrderResponse;
import com.rakesh.razorpay.payment.service.OrderService;
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
@RequestMapping("/v1/orders")
public class OrderController {
    private final OrderService orderService;
    //TODO: replace it with MerchantContext
    UUID merchantId= UUID.fromString("3d5743bb-87e6-4e95-808a-cf094164364d");

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest){
        OrderResponse orderResponse = orderService.createOrder(merchantId,createOrderRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderResponse);
    }
}
