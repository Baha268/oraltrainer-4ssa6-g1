package com.esprit.oral.progress.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress_snapshot", indexes = {
        @Index(name = "idx_snapshot_user_date", columnList = "userId, recordedAt")
})
public class ProgressSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDateTime recordedAt;

    /** Origin of the snapshot: FEEDBACK, VALIDATED_FEEDBACK or SIMULATION (to confirm with the team). */
    @Column(nullable = false, length = 30)
    private String source;

    private double pronunciation;
    private double fluency;
    private double intonation;
    private double lexicon;
    private double grammar;
    private double coherence;

    /** CEFR level, e.g. B1..C2. */
    @Column(length = 5)
    private String estimatedLevel;

    /** Used for idempotence and traceability (not in the class diagram: to confirm). */
    @Column(unique = true, length = 64)
    private String eventId;
    private Long feedbackId;
    private Long recordingId;
    private Long simulationId;

    @PrePersist
    void onCreate() {
        if (recordedAt == null) recordedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public double getPronunciation() { return pronunciation; }
    public void setPronunciation(double v) { this.pronunciation = v; }
    public double getFluency() { return fluency; }
    public void setFluency(double v) { this.fluency = v; }
    public double getIntonation() { return intonation; }
    public void setIntonation(double v) { this.intonation = v; }
    public double getLexicon() { return lexicon; }
    public void setLexicon(double v) { this.lexicon = v; }
    public double getGrammar() { return grammar; }
    public void setGrammar(double v) { this.grammar = v; }
    public double getCoherence() { return coherence; }
    public void setCoherence(double v) { this.coherence = v; }
    public String getEstimatedLevel() { return estimatedLevel; }
    public void setEstimatedLevel(String estimatedLevel) { this.estimatedLevel = estimatedLevel; }
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public Long getFeedbackId() { return feedbackId; }
    public void setFeedbackId(Long feedbackId) { this.feedbackId = feedbackId; }
    public Long getRecordingId() { return recordingId; }
    public void setRecordingId(Long recordingId) { this.recordingId = recordingId; }
    public Long getSimulationId() { return simulationId; }
    public void setSimulationId(Long simulationId) { this.simulationId = simulationId; }
}
