package com.oraltrainer.content.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String instructions;

    @Column(name = "error_tag", length = 50)
    private String errorTag; // e.g. "PRONUNCIATION", "INTONATION", "GRAMMAR", "VOCABULARY"

    @Column(name = "difficulty_level", length = 10)
    private String difficultyLevel; // e.g. "B1", "B2", "C1", "C2"

    @Column(name = "max_score")
    private Integer maxScore = 20;

    @JsonBackReference(value = "lesson-exercises")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    public Exercise() {
    }

    public Exercise(String title, String instructions, String errorTag, String difficultyLevel, Integer maxScore, Lesson lesson) {
        this.title = title;
        this.instructions = instructions;
        this.errorTag = errorTag;
        this.difficultyLevel = difficultyLevel;
        this.maxScore = maxScore;
        this.lesson = lesson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getErrorTag() {
        return errorTag;
    }

    public void setErrorTag(String errorTag) {
        this.errorTag = errorTag;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}
