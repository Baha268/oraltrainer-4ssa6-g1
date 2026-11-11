package com.oraltrainer.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Données pour créer ou modifier un cours")
public record CourseRequestDTO(
        @NotBlank(message = "Le titre du cours est obligatoire")
        @Size(max = 150, message = "Le titre ne doit pas dépasser 150 caractères")
        @Schema(description = "Titre du cours", example = "Préparation DELF B2 - Expression Orale")
        String title,

        @Size(max = 1000, message = "La description ne doit pas dépasser 1000 caractères")
        @Schema(description = "Description détaillée du cours", example = "Entraînement intensif à l'argumentation orale.")
        String description,

        @NotBlank(message = "La langue est obligatoire")
        @Schema(description = "Langue d'apprentissage", example = "FRENCH")
        String language,

        @NotBlank(message = "Le niveau CECRL est obligatoire")
        @Schema(description = "Niveau CECRL visé", example = "B2")
        String level
) {
}
