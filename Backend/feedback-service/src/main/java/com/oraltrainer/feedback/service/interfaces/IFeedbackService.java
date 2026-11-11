package com.oraltrainer.feedback.service.interfaces;

import com.oraltrainer.feedback.entity.Feedback;
import org.springframework.data.domain.Page;

public interface IFeedbackService {

    Feedback getFeedbackById(Long id);

    Feedback getFeedbackByRecordingId(Long recordingId);

    /** Historique paginé ; si userId est null, retourne tous les feedbacks (vue formateur). */
    Page<Feedback> getFeedbacks(Long userId, int page, int size);
}
