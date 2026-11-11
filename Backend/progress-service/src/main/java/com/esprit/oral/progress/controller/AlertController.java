package com.esprit.oral.progress.controller;

import com.esprit.oral.progress.dto.AlertResponse;
import com.esprit.oral.progress.dto.PageResponse;
import com.esprit.oral.progress.entity.AlertStatus;
import com.esprit.oral.progress.service.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alerts")
@Tag(name = "Alerts", description = "Trainer and institution alerts")
public class AlertController {

    private final AlertService service;

    public AlertController(AlertService service) {
        this.service = service;
    }

    @Operation(summary = "List alerts, paginated (200)")
    @GetMapping
    public PageResponse<AlertResponse> list(
            @RequestParam(required = false) Long groupId,
            @RequestParam(required = false) AlertStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return service.list(groupId, status, page, size);
    }

    @Operation(summary = "Acknowledge an alert (200, 404)")
    @PutMapping("/{id}/ack")
    public AlertResponse acknowledge(@PathVariable Long id) {
        return service.acknowledge(id);
    }
}
