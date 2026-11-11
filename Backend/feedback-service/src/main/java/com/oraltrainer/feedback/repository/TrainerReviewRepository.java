package com.oraltrainer.feedback.repository;

import com.oraltrainer.feedback.entity.TrainerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerReviewRepository extends JpaRepository<TrainerReview, Long> {

    Optional<TrainerReview> findByFeedback_Id(Long feedbackId);
}
