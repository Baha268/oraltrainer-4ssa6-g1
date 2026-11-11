package com.oraltrainer.content.mapper;

import com.oraltrainer.content.dto.CourseRequestDTO;
import com.oraltrainer.content.dto.CourseResponseDTO;
import com.oraltrainer.content.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public Course toEntity(CourseRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Course(
                dto.title(),
                dto.description(),
                dto.language(),
                dto.level()
        );
    }

    public void updateEntityFromDto(CourseRequestDTO dto, Course entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setTitle(dto.title());
        entity.setDescription(dto.description());
        entity.setLanguage(dto.language());
        entity.setLevel(dto.level());
    }

    public CourseResponseDTO toResponseDTO(Course entity) {
        if (entity == null) {
            return null;
        }
        int modulesCount = entity.getModules() != null ? entity.getModules().size() : 0;
        return new CourseResponseDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getLanguage(),
                entity.getLevel(),
                modulesCount,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
