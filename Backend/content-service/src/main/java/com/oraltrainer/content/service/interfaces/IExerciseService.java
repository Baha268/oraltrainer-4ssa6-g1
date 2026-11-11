package com.oraltrainer.content.service.interfaces;

import com.oraltrainer.content.entity.Exercise;
import java.util.List;

public interface IExerciseService {
    Exercise createExercise(Exercise exercise);
    Exercise getExerciseById(Long id);
    List<Exercise> getExercisesByLessonId(Long lessonId);
    List<Exercise> getExercisesByErrorTagAndLevel(String errorTag, String level);
    Exercise updateExercise(Long id, Exercise exercise);
    void deleteExercise(Long id);
}
