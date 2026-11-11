package com.oraltrainer.feedback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur de test permettant de vérifier que le microservice Feedback est démarré.
 */
@RestController
@RequestMapping("/api/feedbacks")
@Tag(name = "Hello Controller", description = "Endpoint de test du microservice Feedback")
public class HelloController {

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Vérifier que le service répond",
            description = "Retourne un message de bienvenue, le nom du service et son statut."
    )
    @ApiResponse(responseCode = "200", description = "Le service est opérationnel")
    public HelloResponse hello() {
        return new HelloResponse("Hello from Feedback Microservice!", "feedback-service", "UP");
    }
}
