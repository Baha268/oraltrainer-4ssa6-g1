package com.oraltrainer.feedback.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * Évaluation d'un feedback par un formateur (0..1 par feedback) :
 * validation, scores ajustés et commentaire.
 */
@Entity
@Table(name = "trainer_reviews")
public class TrainerReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Identifiant du formateur (user-service). */
    @Column(nullable = false)
    private Long trainerId;

    /** Scores ajustés par le formateur, stockés en JSON (ex. {"grammar":60.0}). */
    @Column(length = 1000)
    private String adjustedScores;

    @Column(name = "trainer_comment", length = 2000)
    private String comment;

    private boolean validated;

    private LocalDateTime reviewedAt;

    @OneToOne
    @JoinColumn(name = "feedback_id", nullable = false, unique = true)
    @JsonBackReference("feedback-review")
    private Feedback feedback;

    public TrainerReview() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTrainerId() { return trainerId; }
    public void setTrainerId(Long trainerId) { this.trainerId = trainerId; }

    public String getAdjustedScores() { return adjustedScores; }
    public void setAdjustedScores(String adjustedScores) { this.adjustedScores = adjustedScores; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public boolean isValidated() { return validated; }
    public void setValidated(boolean validated) { this.validated = validated; }

    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }

    public Feedback getFeedback() { return feedback; }
    public void setFeedback(Feedback feedback) { this.feedback = feedback; }
}
