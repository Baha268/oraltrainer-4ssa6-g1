package com.oraltrainer.feedback.service.interfaces;

import com.oraltrainer.feedback.entity.Correction;

import java.util.List;

public interface ICorrectionService {

    List<Correction> getCorrectionsByFeedbackId(Long feedbackId);
}
