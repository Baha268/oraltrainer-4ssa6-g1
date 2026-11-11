package com.oraltrainer.simulation.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estimated_score")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstimatedScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idScore;

    @JsonIgnore
    @ManyToOne
    private Simulation simulation;

    @Enumerated(EnumType.STRING)
    private Criterion criterion;

    // Note sur 100
    private double score;
}
