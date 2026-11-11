package com.esprit.oral.progress.dto;

import com.esprit.oral.progress.entity.AlertStatus;
import com.esprit.oral.progress.entity.AlertType;
import com.esprit.oral.progress.entity.Severity;
import java.time.LocalDateTime;

public record AlertResponse(Long id, Long userId, Long groupId, AlertType type, String message,
                            Severity severity, AlertStatus status, LocalDateTime createdAt) {
}
