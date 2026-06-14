package com.rakesh.razorpay.vault.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "card_tokens")
public class CardToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vault_card_id", nullable = false)
    private VaultCard vaultCard;

    @Column(nullable = false, length = 50, unique = true)
    private String token;

    @Column(name = "merchant_id", nullable = false)
    private UUID customer;

    @Column(name = "merchant_id", nullable = false)
    private UUID merchant;

    private LocalDateTime revokedAt;

}
