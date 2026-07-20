package com.rakesh.razorpay.payment.processor.strategy;

import com.rakesh.razorpay.payment.processor.PaymentProcessor;
import com.rakesh.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.rakesh.razorpay.payment.processor.dto.PaymentProcessorResponse;

public class NetBankingPaymentProcessor implements PaymentProcessor {

    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
        // Implement the logic to process net banking payment using the provided request details.
        // This is a placeholder implementation. You would typically integrate with a payment gateway here.
        return new PaymentProcessorResponse.Success("netBankingProcessorReference123", "netBankingBankReference456");
    }
}
