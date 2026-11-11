package com.oraltrainer.feedback.service.implementations;

import com.oraltrainer.feedback.entity.RecommendedExercise;
import com.oraltrainer.feedback.exception.ResourceNotFoundException;
import com.oraltrainer.feedback.repository.FeedbackRepository;
import com.oraltrainer.feedback.repository.RecommendedExerciseRepository;
import com.oraltrainer.feedback.service.interfaces.IRecommendedExerciseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RecommendedExerciseServiceImpl implements IRecommendedExerciseService {

    private final RecommendedExerciseRepository recommendedExerciseRepository;
    private final FeedbackRepository feedbackRepository;

    public RecommendedExerciseServiceImpl(RecommendedExerciseRepository recommendedExerciseRepository,
                                          FeedbackRepository feedbackRepository) {
        this.recommendedExerciseRepository = recommendedExerciseRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public List<RecommendedExercise> getRecommendationsByUserId(Long userId) {
        if (!feedbackRepository.existsByUserId(userId)) {
            throw new ResourceNotFoundException("Aucun feedback pour l'utilisateur " + userId + ".");
        }
        return recommendedExerciseRepository.findByFeedback_UserId(userId);
    }
}
