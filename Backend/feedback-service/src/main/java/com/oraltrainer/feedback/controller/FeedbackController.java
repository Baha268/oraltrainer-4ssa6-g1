package com.oraltrainer.feedback.controller;

import com.oraltrainer.feedback.dto.PageResponse;
import com.oraltrainer.feedback.entity.Correction;
import com.oraltrainer.feedback.entity.Feedback;
import com.oraltrainer.feedback.service.interfaces.ICorrectionService;
import com.oraltrainer.feedback.service.interfaces.IFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@Tag(name = "Feedbacks", description = "Consultation des feedbacks et de leurs corrections")
public class FeedbackController {

    private final IFeedbackService feedbackService;
    private final ICorrectionService correctionService;

    public FeedbackController(IFeedbackService feedbackService, ICorrectionService correctionService) {
        this.feedbackService = feedbackService;
        this.correctionService = correctionService;
    }

    @GetMapping
    @Operation(summary = "Historique des feedbacks (paginé)",
            description = "Filtre par apprenant avec userId ; sans userId, retourne tous les feedbacks (vue formateur).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page de feedbacks"),
            @ApiResponse(responseCode = "400", description = "Paramètres de pagination invalides")
    })
    public PageResponse<Feedback> getFeedbacks(
            @Parameter(description = "Identifiant de l'apprenant", example = "7")
            @RequestParam(required = false) Long userId,
            @Parameter(description = "Numéro de page (à partir de 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page (1 à 100)") @RequestParam(defaultValue = "10") int size) {
        return PageResponse.from(feedbackService.getFeedbacks(userId, page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Détail d'un feedback",
            description = "Scores, commentaire global, corrections, exercices recommandés et évaluation du formateur.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Feedback trouvé"),
            @ApiResponse(responseCode = "404", description = "Feedback introuvable")
    })
    public Feedback getFeedbackById(@PathVariable Long id) {
        return feedbackService.getFeedbackById(id);
    }

    @GetMapping("/recording/{recordingId}")
    @Operation(summary = "Feedback d'un enregistrement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Feedback trouvé"),
            @ApiResponse(responseCode = "404", description = "Aucun feedback pour cet enregistrement")
    })
    public Feedback getFeedbackByRecordingId(@PathVariable Long recordingId) {
        return feedbackService.getFeedbackByRecordingId(recordingId);
    }

    @GetMapping("/{id}/corrections")
    @Operation(summary = "Corrections détaillées d'un feedback",
            description = "Corrections de grammaire, lexique et cohérence, triées par position.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des corrections"),
            @ApiResponse(responseCode = "404", description = "Feedback introuvable")
    })
    public List<Correction> getCorrections(@PathVariable Long id) {
        return correctionService.getCorrectionsByFeedbackId(id);
    }
}
