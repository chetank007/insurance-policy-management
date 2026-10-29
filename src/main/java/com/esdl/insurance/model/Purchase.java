package com.esdl.insurance.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "policy_purchases")
public class Purchase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "policy_id")
    private Policy policy;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private LocalDateTime purchasedAt;
    @Column(nullable = false)
    private String status = "ACTIVE";

    public Purchase() {}
    public Purchase(Policy policy, String username) {
        this.policy = policy; this.username = username; this.purchasedAt = LocalDateTime.now(); this.status = "ACTIVE";
    }
    public Long getId() { return id; }
    public Policy getPolicy() { return policy; }
    public String getUsername() { return username; }
    public LocalDateTime getPurchasedAt() { return purchasedAt; }
    public String getStatus() { return status; }
}
