package com.oraltrainer.feedback.service.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oraltrainer.feedback.dto.CommentRequest;
import com.oraltrainer.feedback.dto.ReviewRequest;
import com.oraltrainer.feedback.entity.Feedback;
import com.oraltrainer.feedback.entity.FeedbackStatus;
import com.oraltrainer.feedback.entity.TrainerReview;
import com.oraltrainer.feedback.exception.InvalidStateException;
import com.oraltrainer.feedback.exception.ResourceNotFoundException;
import com.oraltrainer.feedback.repository.FeedbackRepository;
import com.oraltrainer.feedback.repository.TrainerReviewRepository;
import com.oraltrainer.feedback.service.interfaces.ITrainerReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Transactional
public class TrainerReviewServiceImpl implements ITrainerReviewService {

    private final FeedbackRepository feedbackRepository;
    private final TrainerReviewRepository trainerReviewRepository;
    private final ObjectMapper objectMapper;

    public TrainerReviewServiceImpl(FeedbackRepository feedbackRepository,
                                    TrainerReviewRepository trainerReviewRepository,
                                    ObjectMapper objectMapper) {
        this.feedbackRepository = feedbackRepository;
        this.trainerReviewRepository = trainerReviewRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Feedback reviewFeedback(Long feedbackId, ReviewRequest request) {
        Feedback feedback = findFeedback(feedbackId);

        if (feedback.getStatus() != FeedbackStatus.GENERATED) {
            throw new InvalidStateException(
                    "Le feedback " + feedbackId + " a déjà été évalué (statut " + feedback.getStatus() + ").");
        }

        Map<String, Double> adjusted = new LinkedHashMap<>();
        putIfPresent(adjusted, "pronunciation", request.pronunciation());
        putIfPresent(adjusted, "fluency", request.fluency());
        putIfPresent(adjusted, "intonation", request.intonation());
        putIfPresent(adjusted, "lexicon", request.lexicon());
        putIfPresent(adjusted, "grammar", request.grammar());
        putIfPresent(adjusted, "coherence", request.coherence());

        TrainerReview review = getOrCreateReview(feedback);
        review.setTrainerId(request.trainerId());
        review.setValidated(true);
        review.setReviewedAt(LocalDateTime.now());
        review.setAdjustedScores(adjusted.isEmpty() ? null : toJson(adjusted));
        if (request.comment() != null && !request.comment().isBlank()) {
            review.setComment(request.comment());
        }
        trainerReviewRepository.save(review);

        feedback.setStatus(adjusted.isEmpty() ? FeedbackStatus.VALIDATED : FeedbackStatus.ADJUSTED);

        // Étape suivante : publier l'événement FeedbackValidated (feedback.validated) sur RabbitMQ.
        return feedback;
    }

    @Override
    public TrainerReview addComment(Long feedbackId, CommentRequest request) {
        Feedback feedback = findFeedback(feedbackId);

        TrainerReview review = getOrCreateReview(feedback);
        review.setTrainerId(request.trainerId());
        review.setComment(request.comment());
        review.setReviewedAt(LocalDateTime.now());
        return trainerReviewRepository.save(review);
    }

    // ----- Méthodes privées -----

    private Feedback findFeedback(Long feedbackId) {
        return feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback " + feedbackId + " introuvable."));
    }

    private TrainerReview getOrCreateReview(Feedback feedback) {
        TrainerReview review = feedback.getTrainerReview();
        if (review == null) {
            review = new TrainerReview();
            feedback.attachTrainerReview(review);
        }
        return review;
    }

    private static void putIfPresent(Map<String, Double> map, String key, Double value) {
        if (value != null) {
            map.put(key, value);
        }
    }

    private String toJson(Map<String, Double> scores) {
        try {
            return objectMapper.writeValueAsString(scores);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Impossible de sérialiser les scores ajustés.", e);
        }
    }
}
