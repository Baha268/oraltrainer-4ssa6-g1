package com.oraltrainer.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Représentation d'un exercice")
public record ExerciseResponseDTO(
        @Schema(description = "Identifiant de l'exercice", example = "1")
        Long id,

        @Schema(description = "Titre de l'exercice", example = "Exercice de prononciation : les voyelles nasales")
        String title,

        @Schema(description = "Consignes détaillées")
        String instructions,

        @Schema(description = "Thématique d'erreur", example = "PRONUNCIATION")
        String errorTag,

        @Schema(description = "Niveau de difficulté", example = "B2")
        String difficultyLevel,

        @Schema(description = "Score maximum", example = "20")
        Integer maxScore,

        @Schema(description = "ID de la leçon parente", example = "1")
        Long lessonId,

        @Schema(description = "Titre de la leçon parente", example = "Leçon 1 : L'introduction percutante")
        String lessonTitle
) {
}
