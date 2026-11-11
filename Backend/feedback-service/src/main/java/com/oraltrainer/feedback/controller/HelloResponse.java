package com.oraltrainer.feedback.controller;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Réponse de l'endpoint Hello")
public record HelloResponse(
        @Schema(example = "Hello from Feedback Microservice!") String message,
        @Schema(example = "feedback-service") String service,
        @Schema(example = "UP") String status
) {
}
