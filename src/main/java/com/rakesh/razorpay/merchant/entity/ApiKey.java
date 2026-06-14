package com.rakesh.razorpay.merchant.entity;

import com.rakesh.razorpay.common.enums.Environment;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "api_keys")
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(length = 50, nullable = false, unique = true)
    private String keyId;

    @Column(length = 200, nullable = false)
    private String keySecretHash;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Environment environment;

    @Column(nullable = false)
    private boolean enabled=true;

    private LocalDateTime lastUsedAt;
    private LocalDateTime rotatedAt;
    private LocalDateTime gracePeriodEndAt;





}
