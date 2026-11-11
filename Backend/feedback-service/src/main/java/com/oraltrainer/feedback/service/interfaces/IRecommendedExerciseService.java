package com.oraltrainer.feedback.service.interfaces;

import com.oraltrainer.feedback.entity.RecommendedExercise;

import java.util.List;

public interface IRecommendedExerciseService {

    List<RecommendedExercise> getRecommendationsByUserId(Long userId);
}
