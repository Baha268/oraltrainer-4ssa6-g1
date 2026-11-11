package com.esprit.oral.progress.controller;

import com.esprit.oral.progress.dto.ProgressDtos.DashboardResponse;
import com.esprit.oral.progress.dto.ProgressDtos.GapResponse;
import com.esprit.oral.progress.dto.ProgressDtos.HistoryPoint;
import com.esprit.oral.progress.dto.ProgressDtos.ProgressResponse;
import com.esprit.oral.progress.service.ProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/progress")
@Tag(name = "Progress", description = "Learner progress, history, gap to target and class dashboard")
public class ProgressController {

    private final ProgressService service;

    public ProgressController(ProgressService service) {
        this.service = service;
    }

    @Operation(summary = "Current progress and estimated level (200, 404)")
    @GetMapping("/users/{userId}")
    public ProgressResponse progress(@PathVariable Long userId) {
        return service.getProgress(userId);
    }

    @Operation(summary = "History for the curves, optional criterion filter (200, 400)")
    @GetMapping("/users/{userId}/history")
    public List<HistoryPoint> history(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) String criterion) {
        return service.getHistory(userId, from, to, criterion);
    }

    @Operation(summary = "Gap to the target level (200, 404, 503 if user-service is down)")
    @GetMapping("/users/{userId}/gap")
    public GapResponse gap(@PathVariable Long userId) {
        return service.getGap(userId);
    }

    @Operation(summary = "Class dashboard (200, 404, 503 if user-service is down)")
    @GetMapping("/groups/{groupId}/dashboard")
    public DashboardResponse dashboard(@PathVariable Long groupId) {
        return service.getDashboard(groupId);
    }
}
