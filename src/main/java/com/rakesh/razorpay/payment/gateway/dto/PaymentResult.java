package com.rakesh.razorpay.payment.gateway.dto;

public sealed interface PaymentResult permits
        //PaymentResult.Success,
        PaymentResult.Failed,
        PaymentResult.Pending {

    record Pending(String registrationReference) implements PaymentResult {}

    //record Success(String processorReference, String bankReference) implements PaymentResult{}

    record Failed(String errorCode, String errorDescription) implements PaymentResult{}
}
