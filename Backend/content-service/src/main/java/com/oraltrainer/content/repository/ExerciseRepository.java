package com.oraltrainer.content.repository;

import com.oraltrainer.content.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByLessonId(Long lessonId);
    List<Exercise> findByErrorTagIgnoreCase(String errorTag);
    List<Exercise> findByErrorTagIgnoreCaseAndDifficultyLevelIgnoreCase(String errorTag, String difficultyLevel);
}
