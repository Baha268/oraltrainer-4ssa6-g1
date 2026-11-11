package com.oraltrainer.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Données pour créer ou modifier un exercice")
public record ExerciseRequestDTO(
        @NotBlank(message = "Le titre de l'exercice est obligatoire")
        @Size(max = 150, message = "Le titre ne doit pas dépasser 150 caractères")
        @Schema(description = "Titre de l'exercice", example = "Exercice de prononciation : les voyelles nasales")
        String title,

        @NotBlank(message = "Les consignes sont obligatoires")
        @Schema(description = "Consignes de l'exercice", example = "Répétez chaque phrase en articulant distinctement.")
        String instructions,

        @Schema(description = "Thématique d'erreur ciblée", example = "PRONUNCIATION")
        String errorTag,

        @Schema(description = "Niveau de difficulté", example = "B2")
        String difficultyLevel,

        @Positive(message = "Le score maximum doit être positif")
        @Schema(description = "Score maximum", example = "20")
        Integer maxScore,

        @NotNull(message = "L'identifiant de la leçon parente est obligatoire")
        @Schema(description = "ID de la leçon associée", example = "1")
        Long lessonId
) {
}
