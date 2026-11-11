package com.esprit.oral.progress.repository;

import com.esprit.oral.progress.entity.Attestation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttestationRepository extends JpaRepository<Attestation, Long> {
    List<Attestation> findByUserId(Long userId);
    boolean existsByUserIdAndCertificationIdAndLevel(Long userId, Long certificationId, String level);
}
