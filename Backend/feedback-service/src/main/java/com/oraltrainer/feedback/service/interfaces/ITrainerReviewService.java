package com.oraltrainer.feedback.service.interfaces;

import com.oraltrainer.feedback.dto.CommentRequest;
import com.oraltrainer.feedback.dto.ReviewRequest;
import com.oraltrainer.feedback.entity.Feedback;
import com.oraltrainer.feedback.entity.TrainerReview;

public interface ITrainerReviewService {

    /** Valide ou ajuste les scores d'un feedback (GENERATED → VALIDATED / ADJUSTED). */
    Feedback reviewFeedback(Long feedbackId, ReviewRequest request);

    /** Ajoute ou remplace le commentaire du formateur sur un feedback. */
    TrainerReview addComment(Long feedbackId, CommentRequest request);
}
