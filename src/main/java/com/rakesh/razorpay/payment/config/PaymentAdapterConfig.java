package com.rakesh.razorpay.payment.config;

import com.rakesh.razorpay.common.enums.PaymentMethods;
import com.rakesh.razorpay.payment.gateway.PaymentAdapter;
import com.rakesh.razorpay.payment.gateway.adapter.CardPaymentAdapter;
import com.rakesh.razorpay.payment.gateway.adapter.NetBankingAdapter;
import com.rakesh.razorpay.payment.gateway.adapter.UPIPaymentAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PaymentAdapterConfig {

    @Bean
    public Map<PaymentMethods, PaymentAdapter> paymentAdapterMap() {
        return Map.of(
                PaymentMethods.CARD, new CardPaymentAdapter(),
                PaymentMethods.UPI, new UPIPaymentAdapter(),
                PaymentMethods.NET_BANKING, new NetBankingAdapter()
        );
    }
}
