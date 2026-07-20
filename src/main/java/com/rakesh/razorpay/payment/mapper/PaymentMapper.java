package com.rakesh.razorpay.payment.mapper;

import com.rakesh.razorpay.payment.dto.response.PaymentResponse;
import com.rakesh.razorpay.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {
    @Mapping(target="orderId",source="order.id")
    PaymentResponse toPaymentResponse(Payment payment);

    @Mapping(target="orderId",source="order.id")
    List<PaymentResponse> toPaymentResponses(List<Payment> payments);
}
