package com.rakesh.razorpay.payment.processor.dto;

//sealed is a Java feature (introduced in Java 17) that lets you restrict which classes or interfaces can implement or extend a class/interface.

public sealed interface PaymentProcessorResponse permits
        PaymentProcessorResponse.Success,
        PaymentProcessorResponse.Failed,
        PaymentProcessorResponse.Pending {

    record Pending(String processorReference) implements PaymentProcessorResponse {}

    record Success(String processorReference, String bankReference) implements PaymentProcessorResponse{}

    record Failed(String errorCode, String errorDescription) implements PaymentProcessorResponse{}
}
