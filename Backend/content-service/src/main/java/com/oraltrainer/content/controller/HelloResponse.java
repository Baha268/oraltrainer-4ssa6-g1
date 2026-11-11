package com.oraltrainer.content.controller;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Réponse du statut du microservice Content")
public record HelloResponse(
        @Schema(description = "Message de salutation et d'état", example = "Hello from Content Microservice!")
        String message,

        @Schema(description = "Nom du microservice", example = "content-service")
        String service,

        @Schema(description = "Statut opérationnel du service", example = "UP")
        String status
) {
}
