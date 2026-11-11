package com.esprit.oral.progress.service;

import com.esprit.oral.progress.dto.AlertResponse;
import com.esprit.oral.progress.dto.PageResponse;
import com.esprit.oral.progress.entity.Alert;
import com.esprit.oral.progress.entity.AlertStatus;
import com.esprit.oral.progress.exception.GlobalExceptionHandler.NotFoundException;
import com.esprit.oral.progress.repository.AlertRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AlertService {

    private final AlertRepository alerts;

    public AlertService(AlertRepository alerts) {
        this.alerts = alerts;
    }

    public PageResponse<AlertResponse> list(Long groupId, AlertStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), 100),
                Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Alert> result;
        if (groupId != null && status != null) {
            result = alerts.findByGroupIdAndStatus(groupId, status, pageable);
        } else if (groupId != null) {
            result = alerts.findByGroupId(groupId, pageable);
        } else if (status != null) {
            result = alerts.findByStatus(status, pageable);
        } else {
            result = alerts.findAll(pageable);
        }
        return new PageResponse<>(result.map(AlertService::toResponse).getContent(),
                result.getNumber(), result.getSize(), result.getTotalElements());
    }

    @Transactional
    public AlertResponse acknowledge(Long id) {
        Alert alert = alerts.findById(id)
                .orElseThrow(() -> new NotFoundException("Alert " + id + " not found"));
        alert.setStatus(AlertStatus.ACKNOWLEDGED);
        return toResponse(alerts.save(alert));
    }

    static AlertResponse toResponse(Alert a) {
        return new AlertResponse(a.getId(), a.getUserId(), a.getGroupId(), a.getType(),
                a.getMessage(), a.getSeverity(), a.getStatus(), a.getCreatedAt());
    }
}
