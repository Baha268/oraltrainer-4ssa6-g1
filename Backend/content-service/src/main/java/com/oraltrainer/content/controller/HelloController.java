package com.oraltrainer.content.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Hello Controller", description = "Endpoints de test et de santé du microservice Content Service")
@RestController
@RequestMapping("/api/content")
public class HelloController {

    @Operation(
            summary = "Hello Microservice",
            description = "Retourne le statut opérationnel et les métadonnées de base du microservice Content Service."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Service opérationnel",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = HelloResponse.class)
            )
    )
    @GetMapping("/hello")
    public ResponseEntity<HelloResponse> getHello() {
        HelloResponse response = new HelloResponse(
                "Hello from Content Microservice!",
                "content-service",
                "UP"
        );
        return ResponseEntity.ok(response);
    }
}
