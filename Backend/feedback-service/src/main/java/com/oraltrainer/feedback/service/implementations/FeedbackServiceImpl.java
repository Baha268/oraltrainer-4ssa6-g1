package com.oraltrainer.feedback.service.implementations;

import com.oraltrainer.feedback.entity.Feedback;
import com.oraltrainer.feedback.exception.ResourceNotFoundException;
import com.oraltrainer.feedback.repository.FeedbackRepository;
import com.oraltrainer.feedback.service.interfaces.IFeedbackService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FeedbackServiceImpl implements IFeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback " + id + " introuvable."));
    }

    @Override
    public Feedback getFeedbackByRecordingId(Long recordingId) {
        return feedbackRepository.findByRecordingId(recordingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aucun feedback pour l'enregistrement " + recordingId + "."));
    }

    @Override
    public Page<Feedback> getFeedbacks(Long userId, int page, int size) {
        if (page < 0 || size < 1 || size > 100) {
            throw new IllegalArgumentException("page doit être >= 0 et size entre 1 et 100.");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return userId == null
                ? feedbackRepository.findAll(pageable)
                : feedbackRepository.findByUserId(userId, pageable);
    }
}
