package com.oraltrainer.feedback.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Correction ponctuelle relevée dans la transcription (grammaire, lexique ou cohérence).
 */
@Entity
@Table(name = "corrections")
public class Correction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Category category;

    /** Passage fautif tel qu'il apparaît dans la transcription. */
    @Column(nullable = false, length = 1000)
    private String originalText;

    /** Reformulation proposée. */
    @Column(nullable = false, length = 1000)
    private String suggestion;

    @Column(length = 1000)
    private String explanation;

    /** Position (ordre) de la correction dans la transcription. */
    @Column(name = "position_index")
    private int position;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "feedback_id", nullable = false)
    @JsonBackReference("feedback-corrections")
    private Feedback feedback;

    public Correction() {
    }

    public Correction(Category category, String originalText, String suggestion, String explanation, int position) {
        this.category = category;
        this.originalText = originalText;
        this.suggestion = suggestion;
        this.explanation = explanation;
        this.position = position;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public String getOriginalText() { return originalText; }
    public void setOriginalText(String originalText) { this.originalText = originalText; }

    public String getSuggestion() { return suggestion; }
    public void setSuggestion(String suggestion) { this.suggestion = suggestion; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }

    public Feedback getFeedback() { return feedback; }
    public void setFeedback(Feedback feedback) { this.feedback = feedback; }
}
