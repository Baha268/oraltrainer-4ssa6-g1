package com.oraltrainer.content.service.implementations;

import com.oraltrainer.content.dto.ExerciseRequestDTO;
import com.oraltrainer.content.dto.ExerciseResponseDTO;
import com.oraltrainer.content.entity.Exercise;
import com.oraltrainer.content.entity.Lesson;
import com.oraltrainer.content.exception.ResourceNotFoundException;
import com.oraltrainer.content.mapper.ExerciseMapper;
import com.oraltrainer.content.repository.ExerciseRepository;
import com.oraltrainer.content.repository.LessonRepository;
import com.oraltrainer.content.service.interfaces.IExerciseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExerciseServiceImpl implements IExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final LessonRepository lessonRepository;
    private final ExerciseMapper exerciseMapper;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, LessonRepository lessonRepository, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.lessonRepository = lessonRepository;
        this.exerciseMapper = exerciseMapper;
    }

    @Override
    public ExerciseResponseDTO createExercise(ExerciseRequestDTO requestDTO) {
        Lesson lesson = lessonRepository.findById(requestDTO.lessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Leçon non trouvée avec l'id : " + requestDTO.lessonId()));
        Exercise exercise = exerciseMapper.toEntity(requestDTO, lesson);
        Exercise savedExercise = exerciseRepository.save(exercise);
        return exerciseMapper.toResponseDTO(savedExercise);
    }

    @Override
    @Transactional(readOnly = true)
    public ExerciseResponseDTO getExerciseById(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercice non trouvé avec l'id : " + id));
        return exerciseMapper.toResponseDTO(exercise);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExerciseResponseDTO> getExercisesByLessonId(Long lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new ResourceNotFoundException("Leçon non trouvée avec l'id : " + lessonId);
        }
        return exerciseRepository.findByLessonId(lessonId).stream()
                .map(exerciseMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExerciseResponseDTO> getExercisesByErrorTagAndLevel(String errorTag, String level) {
        List<Exercise> exercises;
        if (errorTag != null && level != null) {
            exercises = exerciseRepository.findByErrorTagIgnoreCaseAndDifficultyLevelIgnoreCase(errorTag, level);
        } else if (errorTag != null) {
            exercises = exerciseRepository.findByErrorTagIgnoreCase(errorTag);
        } else {
            exercises = exerciseRepository.findAll();
        }
        return exercises.stream()
                .map(exerciseMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ExerciseResponseDTO updateExercise(Long id, ExerciseRequestDTO requestDTO) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercice non trouvé avec l'id : " + id));
        Lesson lesson = null;
        if (requestDTO.lessonId() != null && !requestDTO.lessonId().equals(exercise.getLesson().getId())) {
            lesson = lessonRepository.findById(requestDTO.lessonId())
                    .orElseThrow(() -> new ResourceNotFoundException("Leçon non trouvée avec l'id : " + requestDTO.lessonId()));
        }
        exerciseMapper.updateEntityFromDto(requestDTO, exercise, lesson);
        Exercise updatedExercise = exerciseRepository.save(exercise);
        return exerciseMapper.toResponseDTO(updatedExercise);
    }

    @Override
    public void deleteExercise(Long id) {
        if (!exerciseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Exercice non trouvé avec l'id : " + id);
        }
        exerciseRepository.deleteById(id);
    }
}
