package com.oraltrainer.feedback.repository;

import com.oraltrainer.feedback.entity.Correction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorrectionRepository extends JpaRepository<Correction, Long> {

    List<Correction> findByFeedback_IdOrderByPositionAsc(Long feedbackId);
}
