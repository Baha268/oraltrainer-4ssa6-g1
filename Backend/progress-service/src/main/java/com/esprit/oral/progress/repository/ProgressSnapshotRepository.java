package com.esprit.oral.progress.repository;

import com.esprit.oral.progress.entity.ProgressSnapshot;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressSnapshotRepository extends JpaRepository<ProgressSnapshot, Long> {
    List<ProgressSnapshot> findByUserIdOrderByRecordedAtDesc(Long userId);
    Optional<ProgressSnapshot> findFirstByUserIdOrderByRecordedAtDesc(Long userId);
    List<ProgressSnapshot> findByUserIdAndRecordedAtBetweenOrderByRecordedAtAsc(Long userId, LocalDateTime from, LocalDateTime to);
    Optional<ProgressSnapshot> findFirstByFeedbackIdOrderByRecordedAtDesc(Long feedbackId);
    boolean existsByEventId(String eventId);
}
