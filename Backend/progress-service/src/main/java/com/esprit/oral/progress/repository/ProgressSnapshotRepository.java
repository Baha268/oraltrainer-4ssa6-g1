package com.esprit.oral.progress.repository;

import com.esprit.oral.progress.entity.ProgressSnapshot;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProgressSnapshotRepository extends JpaRepository<ProgressSnapshot, Long> {
    List<ProgressSnapshot> findByUserIdOrderByRecordedAtDesc(Long userId);
    Optional<ProgressSnapshot> findFirstByUserIdOrderByRecordedAtDesc(Long userId);
    List<ProgressSnapshot> findByUserIdAndRecordedAtBetweenOrderByRecordedAtAsc(Long userId, LocalDateTime from, LocalDateTime to);
    Optional<ProgressSnapshot> findFirstByFeedbackIdOrderByRecordedAtDesc(Long feedbackId);
    boolean existsByEventId(String eventId);

    @Query("select distinct s.userId from ProgressSnapshot s order by s.userId")
    List<Long> findDistinctUserIds();
}
