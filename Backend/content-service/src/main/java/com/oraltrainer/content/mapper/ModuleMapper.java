package com.oraltrainer.content.mapper;

import com.oraltrainer.content.dto.ModuleRequestDTO;
import com.oraltrainer.content.dto.ModuleResponseDTO;
import com.oraltrainer.content.entity.Course;
import com.oraltrainer.content.entity.Module;
import org.springframework.stereotype.Component;

@Component
public class ModuleMapper {

    public Module toEntity(ModuleRequestDTO dto, Course course) {
        if (dto == null) {
            return null;
        }
        return new Module(
                dto.title(),
                dto.description(),
                dto.orderIndex() != null ? dto.orderIndex() : 0,
                course
        );
    }

    public void updateEntityFromDto(ModuleRequestDTO dto, Module entity, Course course) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setTitle(dto.title());
        entity.setDescription(dto.description());
        if (dto.orderIndex() != null) {
            entity.setOrderIndex(dto.orderIndex());
        }
        if (course != null) {
            entity.setCourse(course);
        }
    }

    public ModuleResponseDTO toResponseDTO(Module entity) {
        if (entity == null) {
            return null;
        }
        Long courseId = entity.getCourse() != null ? entity.getCourse().getId() : null;
        String courseTitle = entity.getCourse() != null ? entity.getCourse().getTitle() : null;
        int lessonsCount = entity.getLessons() != null ? entity.getLessons().size() : 0;
        return new ModuleResponseDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getOrderIndex(),
                courseId,
                courseTitle,
                lessonsCount
        );
    }
}
