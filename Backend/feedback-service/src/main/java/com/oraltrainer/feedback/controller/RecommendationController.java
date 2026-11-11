package com.oraltrainer.feedback.controller;

import com.oraltrainer.feedback.entity.RecommendedExercise;
import com.oraltrainer.feedback.service.interfaces.IRecommendedExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@Tag(name = "Recommandations", description = "Exercices recommandés à un apprenant")
public class RecommendationController {

    private final IRecommendedExerciseService recommendedExerciseService;

    public RecommendationController(IRecommendedExerciseService recommendedExerciseService) {
        this.recommendedExerciseService = recommendedExerciseService;
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Exercices recommandés à un apprenant",
            description = "Regroupe les exercices recommandés dans tous les feedbacks de l'apprenant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des exercices recommandés"),
            @ApiResponse(responseCode = "404", description = "Aucun feedback pour cet apprenant")
    })
    public List<RecommendedExercise> getRecommendations(@PathVariable Long userId) {
        return recommendedExerciseService.getRecommendationsByUserId(userId);
    }
}
