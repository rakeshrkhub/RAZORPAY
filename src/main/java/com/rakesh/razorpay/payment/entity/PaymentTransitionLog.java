package com.rakesh.razorpay.payment.entity;

import com.rakesh.razorpay.common.entity.BaseEntity;
import com.rakesh.razorpay.common.enums.PaymentActor;
import com.rakesh.razorpay.common.enums.PaymentEvent;
import com.rakesh.razorpay.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payment_transition_log",
indexes = {
        @Index(name = "idx_payment_transition_log_payment_id", columnList = "payment_id")
})
public class PaymentTransitionLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment paymentId;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, name = "from_status")
    private PaymentStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "event", length = 30, nullable = false)
    private PaymentEvent event;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, name = "to_status")
    private PaymentStatus toStatus;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, name = "actor")
    private PaymentActor actor;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

}
