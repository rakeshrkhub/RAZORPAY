package com.rakesh.razorpay.payment.gateway;

import com.rakesh.razorpay.common.enums.PaymentMethods;
import com.rakesh.razorpay.payment.gateway.dto.PaymentRequest;
import com.rakesh.razorpay.payment.gateway.dto.PaymentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentGatewayRouter {

    private final Map<PaymentMethods, PaymentAdapter> paymentAdapters;

    public PaymentResult initiatePayment(PaymentRequest paymentRequest) {
        PaymentAdapter paymentAdapter = paymentAdapters.get(paymentRequest.method());
        if(paymentAdapter == null){
            throw new IllegalArgumentException("No payment adapter found for method: " + paymentRequest.method());
        }
        return paymentAdapter.initiatePayment(paymentRequest);
    }
}
