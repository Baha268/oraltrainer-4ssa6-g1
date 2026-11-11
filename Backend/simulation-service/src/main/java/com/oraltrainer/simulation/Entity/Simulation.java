package com.oraltrainer.simulation.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "simulation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Simulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSimulation;

    // Identifiants des autres microservices (pas de jointure entre services)
    @NotNull
    private Long userId;
    @NotNull
    private Long certificationId;
    private Long subjectId;
    private Long gridId;

    @NotBlank
    @Pattern(regexp = "B1|B2|C1|C2", message = "level doit etre B1, B2, C1 ou C2")
    private String level;
    @NotBlank
    @Pattern(regexp = "fr|en", message = "language doit etre fr ou en")
    private String language;

    @Enumerated(EnumType.STRING)
    private SimulationStatus status = SimulationStatus.CREATED;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime finishedAt;
    private String estimatedLevel;
    private Boolean targetReached;

    @OneToMany(mappedBy = "simulation", cascade = CascadeType.ALL)
    @OrderBy("orderIndex ASC")
    private List<SimulationStep> steps = new ArrayList<>();

    @OneToMany(mappedBy = "simulation", cascade = CascadeType.ALL)
    private List<EstimatedScore> estimatedScores = new ArrayList<>();
}
