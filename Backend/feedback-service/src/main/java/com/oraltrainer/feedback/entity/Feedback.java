package com.oraltrainer.feedback.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Feedback produit pour un enregistrement : 6 scores (3 acoustiques reçus
 * d'analysis-service + 3 textuels calculés ici), commentaire global,
 * corrections, exercices recommandés et éventuelle évaluation du formateur.
 */
@Entity
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Identifiant de l'enregistrement (recording-service) — pas de jointure entre services. */
    @Column(nullable = false, unique = true)
    private Long recordingId;

    /** Identifiant de l'apprenant (user-service). */
    @Column(nullable = false)
    private Long userId;

    /** Langue de la prise de parole : FR ou EN. */
    @Column(nullable = false, length = 2)
    private String language;

    // Scores acoustiques (fournis par analysis-service)
    private double pronunciation;
    private double fluency;
    private double intonation;

    // Scores textuels (calculés par feedback-service)
    private double lexicon;
    private double grammar;
    private double coherence;

    @Column(length = 2000)
    private String globalComment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FeedbackStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    @JsonManagedReference("feedback-corrections")
    private List<Correction> corrections = new ArrayList<>();

    @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("feedback-exercises")
    private List<RecommendedExercise> recommendedExercises = new ArrayList<>();

    @OneToOne(mappedBy = "feedback", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("feedback-review")
    private TrainerReview trainerReview;

    public Feedback() {
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (status == null) {
            status = FeedbackStatus.GENERATED;
        }
    }

    // ----- Méthodes utilitaires pour garder les deux côtés des relations cohérents -----

    public void addCorrection(Correction correction) {
        corrections.add(correction);
        correction.setFeedback(this);
    }

    public void addRecommendedExercise(RecommendedExercise exercise) {
        recommendedExercises.add(exercise);
        exercise.setFeedback(this);
    }

    public void attachTrainerReview(TrainerReview review) {
        this.trainerReview = review;
        review.setFeedback(this);
    }

    // ----- Getters / Setters -----

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRecordingId() { return recordingId; }
    public void setRecordingId(Long recordingId) { this.recordingId = recordingId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public double getPronunciation() { return pronunciation; }
    public void setPronunciation(double pronunciation) { this.pronunciation = pronunciation; }

    public double getFluency() { return fluency; }
    public void setFluency(double fluency) { this.fluency = fluency; }

    public double getIntonation() { return intonation; }
    public void setIntonation(double intonation) { this.intonation = intonation; }

    public double getLexicon() { return lexicon; }
    public void setLexicon(double lexicon) { this.lexicon = lexicon; }

    public double getGrammar() { return grammar; }
    public void setGrammar(double grammar) { this.grammar = grammar; }

    public double getCoherence() { return coherence; }
    public void setCoherence(double coherence) { this.coherence = coherence; }

    public String getGlobalComment() { return globalComment; }
    public void setGlobalComment(String globalComment) { this.globalComment = globalComment; }

    public FeedbackStatus getStatus() { return status; }
    public void setStatus(FeedbackStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<Correction> getCorrections() { return corrections; }
    public void setCorrections(List<Correction> corrections) { this.corrections = corrections; }

    public List<RecommendedExercise> getRecommendedExercises() { return recommendedExercises; }
    public void setRecommendedExercises(List<RecommendedExercise> recommendedExercises) {
        this.recommendedExercises = recommendedExercises;
    }

    public TrainerReview getTrainerReview() { return trainerReview; }
    public void setTrainerReview(TrainerReview trainerReview) { this.trainerReview = trainerReview; }
}
