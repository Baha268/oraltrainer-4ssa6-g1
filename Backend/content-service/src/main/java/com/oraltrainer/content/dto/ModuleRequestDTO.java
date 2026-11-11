package com.oraltrainer.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Données pour créer ou modifier un module")
public record ModuleRequestDTO(
        @NotBlank(message = "Le titre du module est obligatoire")
        @Size(max = 150, message = "Le titre ne doit pas dépasser 150 caractères")
        @Schema(description = "Titre du module", example = "Module 1 : Structurer son exposé")
        String title,

        @Size(max = 1000, message = "La description ne doit pas dépasser 1000 caractères")
        @Schema(description = "Description du module", example = "Méthodologie du plan d'exposé en 3 parties.")
        String description,

        @Schema(description = "Ordre d'affichage du module", example = "1")
        Integer orderIndex,

        @NotNull(message = "L'identifiant du cours rattaché est obligatoire")
        @Schema(description = "ID du cours associé", example = "1")
        Long courseId
) {
}
