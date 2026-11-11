package com.esprit.oral.progress.controller;

import com.esprit.oral.progress.dto.AttestationRequest;
import com.esprit.oral.progress.dto.AttestationResponse;
import com.esprit.oral.progress.service.AttestationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attestations")
@Tag(name = "Attestations", description = "Internal level attestations")
public class AttestationController {

    private final AttestationService service;

    public AttestationController(AttestationService service) {
        this.service = service;
    }

    @Operation(summary = "Issue an attestation if the level is reached",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Attestation issued"),
                    @ApiResponse(responseCode = "400", description = "Invalid request or level not reached"),
                    @ApiResponse(responseCode = "404", description = "No progress for this user"),
                    @ApiResponse(responseCode = "409", description = "Attestation already exists")
            })
    @PostMapping
    public ResponseEntity<AttestationResponse> issue(@Valid @RequestBody AttestationRequest request) {
        AttestationResponse created = service.issue(request);
        return ResponseEntity.created(URI.create("/api/attestations/" + created.id())).body(created);
    }

    @Operation(summary = "Attestations of a learner (200)")
    @GetMapping
    public List<AttestationResponse> list(@RequestParam(required = false) Long userId) {
        return service.list(userId);
    }

    @Operation(summary = "Attestation detail (200, 404)")
    @GetMapping("/{id}")
    public AttestationResponse get(@PathVariable Long id) {
        return service.get(id);
    }
}
