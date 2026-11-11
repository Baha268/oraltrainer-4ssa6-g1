package com.oraltrainer.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Représentation d'un module")
public record ModuleResponseDTO(
        @Schema(description = "Identifiant du module", example = "1")
        Long id,

        @Schema(description = "Titre du module", example = "Module 1 : Structurer son exposé")
        String title,

        @Schema(description = "Description du module")
        String description,

        @Schema(description = "Ordre du module", example = "1")
        Integer orderIndex,

        @Schema(description = "ID du cours parent", example = "1")
        Long courseId,

        @Schema(description = "Titre du cours parent", example = "Préparation DELF B2 - Expression Orale")
        String courseTitle,

        @Schema(description = "Nombre de leçons dans ce module", example = "3")
        int lessonsCount
) {
}
