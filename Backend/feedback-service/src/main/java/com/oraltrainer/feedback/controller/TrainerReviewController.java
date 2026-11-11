package com.oraltrainer.feedback.controller;

import com.oraltrainer.feedback.dto.CommentRequest;
import com.oraltrainer.feedback.dto.ReviewRequest;
import com.oraltrainer.feedback.entity.Feedback;
import com.oraltrainer.feedback.entity.TrainerReview;
import com.oraltrainer.feedback.service.interfaces.ITrainerReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/feedbacks")
@Tag(name = "Évaluation formateur", description = "Validation, ajustement des scores et commentaires du formateur")
public class TrainerReviewController {

    private final ITrainerReviewService trainerReviewService;

    public TrainerReviewController(ITrainerReviewService trainerReviewService) {
        this.trainerReviewService = trainerReviewService;
    }

    @PutMapping("/{id}/review")
    @Operation(summary = "Valider ou ajuster les scores d'un feedback",
            description = "Sans score dans le corps : le feedback passe à VALIDATED. "
                    + "Avec au moins un score (0 à 100) : il passe à ADJUSTED.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Feedback évalué"),
            @ApiResponse(responseCode = "400", description = "Corps invalide"),
            @ApiResponse(responseCode = "404", description = "Feedback introuvable"),
            @ApiResponse(responseCode = "409", description = "Feedback déjà évalué")
    })
    public Feedback reviewFeedback(@PathVariable Long id, @Valid @RequestBody ReviewRequest request) {
        return trainerReviewService.reviewFeedback(id, request);
    }

    @PostMapping("/{id}/comments")
    @Operation(summary = "Ajouter un commentaire de formateur")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Commentaire enregistré"),
            @ApiResponse(responseCode = "400", description = "Corps invalide"),
            @ApiResponse(responseCode = "404", description = "Feedback introuvable")
    })
    public ResponseEntity<TrainerReview> addComment(@PathVariable Long id, @Valid @RequestBody CommentRequest request) {
        TrainerReview review = trainerReviewService.addComment(id, request);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/feedbacks/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).body(review);
    }
}
