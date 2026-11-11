package com.oraltrainer.simulation.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "examiner_prompt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExaminerPrompt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrompt;

    @JsonIgnore
    @OneToOne
    private SimulationStep step;

    @Column(length = 1000)
    private String text;
    private String reason;
    private LocalDateTime selectedAt = LocalDateTime.now();
}
