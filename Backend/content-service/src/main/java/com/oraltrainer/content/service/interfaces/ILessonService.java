package com.oraltrainer.content.service.interfaces;

import com.oraltrainer.content.dto.LessonRequestDTO;
import com.oraltrainer.content.dto.LessonResponseDTO;

import java.util.List;

public interface ILessonService {
    LessonResponseDTO createLesson(LessonRequestDTO requestDTO);
    LessonResponseDTO getLessonById(Long id);
    List<LessonResponseDTO> getLessonsByModuleId(Long moduleId);
    LessonResponseDTO updateLesson(Long id, LessonRequestDTO requestDTO);
    void deleteLesson(Long id);
}
