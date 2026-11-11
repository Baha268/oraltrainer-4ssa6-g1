package com.oraltrainer.simulation.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "simulation_step")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimulationStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStep;

    @JsonIgnore
    @ManyToOne
    private Simulation simulation;

    @Enumerated(EnumType.STRING)
    private StepType type;
    private int orderIndex;
    private int durationSec;

    @Enumerated(EnumType.STRING)
    private StepStatus status = StepStatus.RUNNING;

    private LocalDateTime startedAt = LocalDateTime.now();
    private LocalDateTime endedAt;

    // Identifiant de l'enregistrement (recording-service)
    private Long recordingId;

    @OneToOne(mappedBy = "step", cascade = CascadeType.ALL)
    private ExaminerPrompt prompt;
}
