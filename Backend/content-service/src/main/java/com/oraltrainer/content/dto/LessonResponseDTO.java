package com.oraltrainer.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Représentation d'une leçon")
public record LessonResponseDTO(
        @Schema(description = "Identifiant de la leçon", example = "1")
        Long id,

        @Schema(description = "Titre de la leçon", example = "Leçon 1 : L'introduction percutante")
        String title,

        @Schema(description = "Contenu pédagogique")
        String content,

        @Schema(description = "Durée estimée en minutes", example = "20")
        Integer durationMinutes,

        @Schema(description = "Ordre dans le module", example = "1")
        Integer orderIndex,

        @Schema(description = "ID du module parent", example = "1")
        Long moduleId,

        @Schema(description = "Titre du module parent", example = "Module 1 : Structurer son exposé")
        String moduleTitle,

        @Schema(description = "Nombre d'exercices associés", example = "2")
        int exercisesCount
) {
}
