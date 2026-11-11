package com.oraltrainer.feedback.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Exercice recommandé à l'apprenant pour travailler une erreur récurrente.
 * L'exercice lui-même est géré par content-service : on ne stocke que son identifiant.
 */
@Entity
@Table(name = "recommended_exercises")
public class RecommendedExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Identifiant de l'exercice dans content-service. */
    @Column(nullable = false)
    private Long exerciseId;

    /** Raison de la recommandation (erreur ciblée). */
    @Column(length = 1000)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "feedback_id", nullable = false)
    @JsonBackReference("feedback-exercises")
    private Feedback feedback;

    public RecommendedExercise() {
    }

    public RecommendedExercise(Long exerciseId, String reason) {
        this.exerciseId = exerciseId;
        this.reason = reason;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getExerciseId() { return exerciseId; }
    public void setExerciseId(Long exerciseId) { this.exerciseId = exerciseId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Feedback getFeedback() { return feedback; }
    public void setFeedback(Feedback feedback) { this.feedback = feedback; }
}
