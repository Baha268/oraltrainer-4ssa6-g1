package com.esprit.oral.progress.dto;

import com.esprit.oral.progress.entity.AttestationStatus;
import java.time.LocalDate;

public record AttestationResponse(Long id, String code, Long userId, Long certificationId,
                                  String level, LocalDate issuedAt, Long issuedBy,
                                  AttestationStatus status) {
}
