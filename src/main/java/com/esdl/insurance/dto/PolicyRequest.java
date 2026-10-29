package com.esdl.insurance.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record PolicyRequest(
    @NotBlank String name,
    @NotBlank String description,
    @NotBlank String category,
    @NotNull @DecimalMin("0.01") BigDecimal premiumAmount,
    @NotNull @DecimalMin("1.00") BigDecimal coverageAmount
) {}
