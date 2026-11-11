package com.oraltrainer.feedback.service.implementations;

import com.oraltrainer.feedback.entity.Correction;
import com.oraltrainer.feedback.exception.ResourceNotFoundException;
import com.oraltrainer.feedback.repository.CorrectionRepository;
import com.oraltrainer.feedback.repository.FeedbackRepository;
import com.oraltrainer.feedback.service.interfaces.ICorrectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CorrectionServiceImpl implements ICorrectionService {

    private final CorrectionRepository correctionRepository;
    private final FeedbackRepository feedbackRepository;

    public CorrectionServiceImpl(CorrectionRepository correctionRepository, FeedbackRepository feedbackRepository) {
        this.correctionRepository = correctionRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public List<Correction> getCorrectionsByFeedbackId(Long feedbackId) {
        if (!feedbackRepository.existsById(feedbackId)) {
            throw new ResourceNotFoundException("Feedback " + feedbackId + " introuvable.");
        }
        return correctionRepository.findByFeedback_IdOrderByPositionAsc(feedbackId);
    }
}
