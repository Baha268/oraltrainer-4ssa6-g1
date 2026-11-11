package com.oraltrainer.content.service.implementations;

import com.oraltrainer.content.dto.LessonRequestDTO;
import com.oraltrainer.content.dto.LessonResponseDTO;
import com.oraltrainer.content.entity.Lesson;
import com.oraltrainer.content.entity.Module;
import com.oraltrainer.content.exception.ResourceNotFoundException;
import com.oraltrainer.content.mapper.LessonMapper;
import com.oraltrainer.content.repository.LessonRepository;
import com.oraltrainer.content.repository.ModuleRepository;
import com.oraltrainer.content.service.interfaces.ILessonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LessonServiceImpl implements ILessonService {

    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;
    private final LessonMapper lessonMapper;

    public LessonServiceImpl(LessonRepository lessonRepository, ModuleRepository moduleRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.moduleRepository = moduleRepository;
        this.lessonMapper = lessonMapper;
    }

    @Override
    public LessonResponseDTO createLesson(LessonRequestDTO requestDTO) {
        Module module = moduleRepository.findById(requestDTO.moduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'id : " + requestDTO.moduleId()));
        Lesson lesson = lessonMapper.toEntity(requestDTO, module);
        Lesson savedLesson = lessonRepository.save(lesson);
        return lessonMapper.toResponseDTO(savedLesson);
    }

    @Override
    @Transactional(readOnly = true)
    public LessonResponseDTO getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leçon non trouvée avec l'id : " + id));
        return lessonMapper.toResponseDTO(lesson);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonResponseDTO> getLessonsByModuleId(Long moduleId) {
        if (!moduleRepository.existsById(moduleId)) {
            throw new ResourceNotFoundException("Module non trouvé avec l'id : " + moduleId);
        }
        return lessonRepository.findByModuleIdOrderByOrderIndexAsc(moduleId).stream()
                .map(lessonMapper::toResponseDTO)
                .toList();
    }

    @Override
    public LessonResponseDTO updateLesson(Long id, LessonRequestDTO requestDTO) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leçon non trouvée avec l'id : " + id));
        Module module = null;
        if (requestDTO.moduleId() != null && !requestDTO.moduleId().equals(lesson.getModule().getId())) {
            module = moduleRepository.findById(requestDTO.moduleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Module non trouvé avec l'id : " + requestDTO.moduleId()));
        }
        lessonMapper.updateEntityFromDto(requestDTO, lesson, module);
        Lesson updatedLesson = lessonRepository.save(lesson);
        return lessonMapper.toResponseDTO(updatedLesson);
    }

    @Override
    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Leçon non trouvée avec l'id : " + id);
        }
        lessonRepository.deleteById(id);
    }
}
