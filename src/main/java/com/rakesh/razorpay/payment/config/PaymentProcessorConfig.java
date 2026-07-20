package com.rakesh.razorpay.payment.config;

import com.rakesh.razorpay.common.enums.PaymentMethods;
import com.rakesh.razorpay.payment.processor.PaymentProcessor;
import com.rakesh.razorpay.payment.processor.strategy.CardPaymentProcessor;
import com.rakesh.razorpay.payment.processor.strategy.NetBankingPaymentProcessor;
import com.rakesh.razorpay.payment.processor.strategy.UPIPaymentProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PaymentProcessorConfig {

    @Bean
    public Map<PaymentMethods, PaymentProcessor> paymentProcessorMap() {
        return Map.of(
                PaymentMethods.CARD, new CardPaymentProcessor(),
                PaymentMethods.UPI, new UPIPaymentProcessor(),
                PaymentMethods.NET_BANKING, new NetBankingPaymentProcessor()
        );
    }
}
