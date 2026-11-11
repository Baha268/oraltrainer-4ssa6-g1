package com.oraltrainer.simulation.repository;

import com.oraltrainer.simulation.Entity.SimulationStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationStepRepository extends JpaRepository<SimulationStep, Long> {
}
