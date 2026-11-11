package com.oraltrainer.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Représentation d'un cours complet")
public record CourseResponseDTO(
        @Schema(description = "Identifiant unique du cours", example = "1")
        Long id,

        @Schema(description = "Titre du cours", example = "Préparation DELF B2 - Expression Orale")
        String title,

        @Schema(description = "Description du cours")
        String description,

        @Schema(description = "Langue du cours", example = "FRENCH")
        String language,

        @Schema(description = "Niveau CECRL visé", example = "B2")
        String level,

        @Schema(description = "Nombre de modules contenus dans le cours", example = "4")
        int modulesCount,

        @Schema(description = "Date de création")
        LocalDateTime createdAt,

        @Schema(description = "Date de dernière mise à jour")
        LocalDateTime updatedAt
) {
}
