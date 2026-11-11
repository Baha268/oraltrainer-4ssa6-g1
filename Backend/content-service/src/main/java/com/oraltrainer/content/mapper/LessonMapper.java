package com.oraltrainer.content.mapper;

import com.oraltrainer.content.dto.LessonRequestDTO;
import com.oraltrainer.content.dto.LessonResponseDTO;
import com.oraltrainer.content.entity.Lesson;
import com.oraltrainer.content.entity.Module;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {

    public Lesson toEntity(LessonRequestDTO dto, Module module) {
        if (dto == null) {
            return null;
        }
        return new Lesson(
                dto.title(),
                dto.content(),
                dto.durationMinutes() != null ? dto.durationMinutes() : 15,
                dto.orderIndex() != null ? dto.orderIndex() : 0,
                module
        );
    }

    public void updateEntityFromDto(LessonRequestDTO dto, Lesson entity, Module module) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setTitle(dto.title());
        entity.setContent(dto.content());
        if (dto.durationMinutes() != null) {
            entity.setDurationMinutes(dto.durationMinutes());
        }
        if (dto.orderIndex() != null) {
            entity.setOrderIndex(dto.orderIndex());
        }
        if (module != null) {
            entity.setModule(module);
        }
    }

    public LessonResponseDTO toResponseDTO(Lesson entity) {
        if (entity == null) {
            return null;
        }
        Long moduleId = entity.getModule() != null ? entity.getModule().getId() : null;
        String moduleTitle = entity.getModule() != null ? entity.getModule().getTitle() : null;
        int exercisesCount = entity.getExercises() != null ? entity.getExercises().size() : 0;
        return new LessonResponseDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getDurationMinutes(),
                entity.getOrderIndex(),
                moduleId,
                moduleTitle,
                exercisesCount
        );
    }
}
