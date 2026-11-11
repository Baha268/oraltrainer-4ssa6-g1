package com.oraltrainer.simulation.repository;

import com.oraltrainer.simulation.Entity.Simulation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationRepository extends JpaRepository<Simulation, Long> {
    Page<Simulation> findByUserId(Long userId, Pageable pageable);
}
