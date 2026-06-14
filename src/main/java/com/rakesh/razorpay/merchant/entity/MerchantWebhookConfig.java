package com.rakesh.razorpay.merchant.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "merchant_webhook_config")
public class MerchantWebhookConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(nullable = false, length = 500)
    private String targetUrl;

    @Column(length = 255)
    private String eventTypes;

    @Column(nullable = false)
    private boolean enabled=true;

    @Column(length = 255)
    private String webhookSecretHash;

    private LocalDateTime createdAt;
}
