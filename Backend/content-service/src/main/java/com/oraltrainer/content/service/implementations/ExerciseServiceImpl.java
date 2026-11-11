package com.oraltrainer.content.service.implementations;

import com.oraltrainer.content.entity.Exercise;
import com.oraltrainer.content.repository.ExerciseRepository;
import com.oraltrainer.content.service.interfaces.IExerciseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExerciseServiceImpl implements IExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public Exercise createExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    @Transactional(readOnly = true)
    public Exercise getExerciseById(Long id) {
        return exerciseRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Exercise> getExercisesByLessonId(Long lessonId) {
        return exerciseRepository.findByLessonId(lessonId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Exercise> getExercisesByErrorTagAndLevel(String errorTag, String level) {
        if (errorTag != null && level != null) {
            return exerciseRepository.findByErrorTagIgnoreCaseAndDifficultyLevelIgnoreCase(errorTag, level);
        } else if (errorTag != null) {
            return exerciseRepository.findByErrorTagIgnoreCase(errorTag);
        }
        return exerciseRepository.findAll();
    }

    @Override
    public Exercise updateExercise(Long id, Exercise exerciseDetails) {
        Exercise exercise = exerciseRepository.findById(id).orElse(null);
        if (exercise != null) {
            exercise.setTitle(exerciseDetails.getTitle());
            exercise.setInstructions(exerciseDetails.getInstructions());
            exercise.setErrorTag(exerciseDetails.getErrorTag());
            exercise.setDifficultyLevel(exerciseDetails.getDifficultyLevel());
            exercise.setMaxScore(exerciseDetails.getMaxScore());
            if (exerciseDetails.getLesson() != null) {
                exercise.setLesson(exerciseDetails.getLesson());
            }
            return exerciseRepository.save(exercise);
        }
        return null;
    }

    @Override
    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }
}
