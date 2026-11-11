package com.oraltrainer.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Données pour créer ou modifier une leçon")
public record LessonRequestDTO(
        @NotBlank(message = "Le titre de la leçon est obligatoire")
        @Size(max = 150, message = "Le titre ne doit pas dépasser 150 caractères")
        @Schema(description = "Titre de la leçon", example = "Leçon 1 : L'introduction percutante")
        String title,

        @Schema(description = "Contenu textuel ou pédagogique de la leçon", example = "Conseils pour capter l'attention de l'auditoire...")
        String content,

        @Positive(message = "La durée doit être positive")
        @Schema(description = "Durée estimée en minutes", example = "20")
        Integer durationMinutes,

        @Schema(description = "Ordre d'affichage de la leçon", example = "1")
        Integer orderIndex,

        @NotNull(message = "L'identifiant du module parent est obligatoire")
        @Schema(description = "ID du module parent", example = "1")
        Long moduleId
) {
}
