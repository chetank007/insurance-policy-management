package com.esdl.insurance.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "claims")
public class Claim {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "purchase_id")
    private Purchase purchase;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false, length = 1500)
    private String incidentDetails;
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal claimAmount;
    @Column(nullable = false)
    private String documentPath;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private ClaimStatus status = ClaimStatus.SUBMITTED;
    private String adminRemarks;
    @Column(nullable = false)
    private LocalDateTime submittedAt;
    private LocalDateTime reviewedAt;

    public Claim() {}
    public Claim(Purchase purchase, String username, String incidentDetails, BigDecimal claimAmount, String documentPath) {
        this.purchase = purchase; this.username = username; this.incidentDetails = incidentDetails;
        this.claimAmount = claimAmount; this.documentPath = documentPath;
        this.status = ClaimStatus.SUBMITTED; this.submittedAt = LocalDateTime.now();
    }
    public Long getId() { return id; }
    public Purchase getPurchase() { return purchase; }
    public String getUsername() { return username; }
    public String getIncidentDetails() { return incidentDetails; }
    public BigDecimal getClaimAmount() { return claimAmount; }
    public String getDocumentPath() { return documentPath; }
    public ClaimStatus getStatus() { return status; }
    public void setStatus(ClaimStatus status) { this.status = status; }
    public String getAdminRemarks() { return adminRemarks; }
    public void setAdminRemarks(String adminRemarks) { this.adminRemarks = adminRemarks; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
}
