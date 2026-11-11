package com.oraltrainer.feedback.repository;

import com.oraltrainer.feedback.entity.RecommendedExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendedExerciseRepository extends JpaRepository<RecommendedExercise, Long> {

    List<RecommendedExercise> findByFeedback_UserId(Long userId);
}
