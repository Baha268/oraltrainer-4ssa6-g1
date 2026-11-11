package com.oraltrainer.content.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Structure standard des erreurs de l'API")
public record ErrorResponseDTO(
        @Schema(description = "Date et heure de l'erreur", example = "2026-11-11T11:00:00")
        LocalDateTime timestamp,

        @Schema(description = "Code de statut HTTP", example = "404")
        int status,

        @Schema(description = "Type d'erreur HTTP", example = "Not Found")
        String error,

        @Schema(description = "Message explicatif de l'erreur", example = "Course non trouvé avec l'id : 1")
        String message,

        @Schema(description = "Chemin de la requête ayant échoué", example = "/api/courses/1")
        String path
) {
}
