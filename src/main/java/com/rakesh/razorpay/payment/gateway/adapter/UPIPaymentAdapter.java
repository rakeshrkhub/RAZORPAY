package com.rakesh.razorpay.payment.gateway.adapter;

import com.rakesh.razorpay.payment.gateway.PaymentAdapter;
import com.rakesh.razorpay.payment.gateway.dto.PaymentRequest;
import com.rakesh.razorpay.payment.gateway.dto.PaymentResult;

public class UPIPaymentAdapter implements PaymentAdapter {

    @Override
    public PaymentResult initiatePayment(PaymentRequest paymentRequest) {
        // Implement UPI payment processing logic here
        return null;
    }
}
