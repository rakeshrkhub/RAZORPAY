package com.rakesh.razorpay.operations.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;
@Embeddable
public class SettlementPaymentId {
    private UUID settlementId;
    private UUID paymentId;
}
