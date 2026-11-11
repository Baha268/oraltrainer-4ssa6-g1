package com.oraltrainer.content.mapper;

import com.oraltrainer.content.dto.ExerciseRequestDTO;
import com.oraltrainer.content.dto.ExerciseResponseDTO;
import com.oraltrainer.content.entity.Exercise;
import com.oraltrainer.content.entity.Lesson;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper {

    public Exercise toEntity(ExerciseRequestDTO dto, Lesson lesson) {
        if (dto == null) {
            return null;
        }
        return new Exercise(
                dto.title(),
                dto.instructions(),
                dto.errorTag(),
                dto.difficultyLevel(),
                dto.maxScore() != null ? dto.maxScore() : 20,
                lesson
        );
    }

    public void updateEntityFromDto(ExerciseRequestDTO dto, Exercise entity, Lesson lesson) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setTitle(dto.title());
        entity.setInstructions(dto.instructions());
        entity.setErrorTag(dto.errorTag());
        entity.setDifficultyLevel(dto.difficultyLevel());
        if (dto.maxScore() != null) {
            entity.setMaxScore(dto.maxScore());
        }
        if (lesson != null) {
            entity.setLesson(lesson);
        }
    }

    public ExerciseResponseDTO toResponseDTO(Exercise entity) {
        if (entity == null) {
            return null;
        }
        Long lessonId = entity.getLesson() != null ? entity.getLesson().getId() : null;
        String lessonTitle = entity.getLesson() != null ? entity.getLesson().getTitle() : null;
        return new ExerciseResponseDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getInstructions(),
                entity.getErrorTag(),
                entity.getDifficultyLevel(),
                entity.getMaxScore(),
                lessonId,
                lessonTitle
        );
    }
}
