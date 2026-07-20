package com.rakesh.razorpay.payment.processor;

import com.rakesh.razorpay.common.enums.PaymentMethods;
import com.rakesh.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.rakesh.razorpay.payment.processor.dto.PaymentProcessorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentProcessorRouter {

    private final Map<PaymentMethods, PaymentProcessor> paymentProcessors;

    public PaymentProcessorResponse route(PaymentProcessorRequest paymentProcessorRequest) {
        PaymentProcessor paymentProcessor = paymentProcessors.get(paymentProcessorRequest.methods());
        if(paymentProcessor == null){
            throw new IllegalArgumentException("No payment processor registered for method: " + paymentProcessorRequest.methods());
        }

        return paymentProcessor.charge(paymentProcessorRequest);
    }
}
