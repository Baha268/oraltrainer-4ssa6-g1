package com.oraltrainer.simulation.repository;

import com.oraltrainer.simulation.Entity.EstimatedScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimatedScoreRepository extends JpaRepository<EstimatedScore, Long> {
}
