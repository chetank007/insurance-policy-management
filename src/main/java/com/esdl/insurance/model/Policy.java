package com.esdl.insurance.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "policies")
public class Policy {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(nullable = false)
    private String name;
    @NotBlank @Column(nullable = false, length = 1000)
    private String description;
    @NotBlank @Column(nullable = false)
    private String category;
    @NotNull @DecimalMin("0.01") @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal premiumAmount;
    @NotNull @DecimalMin("1.00") @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal coverageAmount;
    private boolean active = true;

    public Policy() {}
    public Policy(String name, String description, String category, BigDecimal premiumAmount, BigDecimal coverageAmount) {
        this.name = name; this.description = description; this.category = category;
        this.premiumAmount = premiumAmount; this.coverageAmount = coverageAmount; this.active = true;
    }
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public BigDecimal getPremiumAmount() { return premiumAmount; }
    public void setPremiumAmount(BigDecimal premiumAmount) { this.premiumAmount = premiumAmount; }
    public BigDecimal getCoverageAmount() { return coverageAmount; }
    public void setCoverageAmount(BigDecimal coverageAmount) { this.coverageAmount = coverageAmount; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
