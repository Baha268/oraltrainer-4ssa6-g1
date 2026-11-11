package com.oraltrainer.simulation.repository;

import com.oraltrainer.simulation.Entity.ExaminerPrompt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExaminerPromptRepository extends JpaRepository<ExaminerPrompt, Long> {
}
