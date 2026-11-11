package com.oraltrainer.content.service.interfaces;

import com.oraltrainer.content.dto.ExerciseRequestDTO;
import com.oraltrainer.content.dto.ExerciseResponseDTO;

import java.util.List;

public interface IExerciseService {
    ExerciseResponseDTO createExercise(ExerciseRequestDTO requestDTO);
    ExerciseResponseDTO getExerciseById(Long id);
    List<ExerciseResponseDTO> getExercisesByLessonId(Long lessonId);
    List<ExerciseResponseDTO> getExercisesByErrorTagAndLevel(String errorTag, String level);
    ExerciseResponseDTO updateExercise(Long id, ExerciseRequestDTO requestDTO);
    void deleteExercise(Long id);
}
