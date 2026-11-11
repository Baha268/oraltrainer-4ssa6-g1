package com.esprit.oral.progress.repository;

import com.esprit.oral.progress.entity.Alert;
import com.esprit.oral.progress.entity.AlertStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    Page<Alert> findByGroupId(Long groupId, Pageable pageable);
    Page<Alert> findByStatus(AlertStatus status, Pageable pageable);
    Page<Alert> findByGroupIdAndStatus(Long groupId, AlertStatus status, Pageable pageable);
}
