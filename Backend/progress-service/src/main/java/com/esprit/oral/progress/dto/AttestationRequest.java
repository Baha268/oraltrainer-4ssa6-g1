package com.esprit.oral.progress.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AttestationRequest(
        @NotNull Long userId,
        @NotNull Long certificationId,
        @NotBlank String level,
        @NotNull Long issuedBy) {
}
