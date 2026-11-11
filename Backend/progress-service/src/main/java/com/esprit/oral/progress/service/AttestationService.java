package com.esprit.oral.progress.service;

import com.esprit.oral.progress.dto.AttestationRequest;
import com.esprit.oral.progress.dto.AttestationResponse;
import com.esprit.oral.progress.entity.Attestation;
import com.esprit.oral.progress.entity.ProgressSnapshot;
import com.esprit.oral.progress.exception.GlobalExceptionHandler.BadRequestException;
import com.esprit.oral.progress.exception.GlobalExceptionHandler.ConflictException;
import com.esprit.oral.progress.exception.GlobalExceptionHandler.NotFoundException;
import com.esprit.oral.progress.repository.AttestationRepository;
import com.esprit.oral.progress.repository.ProgressSnapshotRepository;
import com.esprit.oral.progress.util.Levels;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PLACEHOLDER RULE (to confirm with the team): an attestation can be issued when the learner's
 * latest estimated level is at least the requested level. S6: also check the certification
 * through content-service (GET /api/certifications/{id}).
 */
@Service
@Transactional(readOnly = true)
public class AttestationService {

    private final AttestationRepository attestations;
    private final ProgressSnapshotRepository snapshots;

    public AttestationService(AttestationRepository attestations, ProgressSnapshotRepository snapshots) {
        this.attestations = attestations;
        this.snapshots = snapshots;
    }

    @Transactional
    public AttestationResponse issue(AttestationRequest req) {
        String level = req.level().trim().toUpperCase();
        if (!Levels.isValid(level)) {
            throw new BadRequestException("Unknown level '" + req.level() + "'. Allowed: A1, A2, B1, B2, C1, C2");
        }
        ProgressSnapshot latest = snapshots.findFirstByUserIdOrderByRecordedAtDesc(req.userId())
                .orElseThrow(() -> new NotFoundException("No progress found for user " + req.userId()));
        if (Levels.rank(latest.getEstimatedLevel()) < Levels.rank(level)) {
            throw new BadRequestException("Level " + level + " not reached (current estimate: "
                    + latest.getEstimatedLevel() + ")");
        }
        if (attestations.existsByUserIdAndCertificationIdAndLevel(req.userId(), req.certificationId(), level)) {
            throw new ConflictException("An attestation already exists for this user, certification and level");
        }
        Attestation a = new Attestation();
        a.setCode("ATT-" + LocalDate.now().getYear() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        a.setUserId(req.userId());
        a.setCertificationId(req.certificationId());
        a.setLevel(level);
        a.setIssuedBy(req.issuedBy());
        return toResponse(attestations.save(a));
    }

    public List<AttestationResponse> list(Long userId) {
        List<Attestation> found = userId == null ? attestations.findAll() : attestations.findByUserId(userId);
        return found.stream().map(AttestationService::toResponse).toList();
    }

    public AttestationResponse get(Long id) {
        return attestations.findById(id).map(AttestationService::toResponse)
                .orElseThrow(() -> new NotFoundException("Attestation " + id + " not found"));
    }

    static AttestationResponse toResponse(Attestation a) {
        return new AttestationResponse(a.getId(), a.getCode(), a.getUserId(), a.getCertificationId(),
                a.getLevel(), a.getIssuedAt(), a.getIssuedBy(), a.getStatus());
    }
}
