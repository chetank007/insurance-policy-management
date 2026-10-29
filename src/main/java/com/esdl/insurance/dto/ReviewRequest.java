package com.esdl.insurance.dto;

import jakarta.validation.constraints.NotBlank;
public record ReviewRequest(@NotBlank String decision, String remarks) {}
