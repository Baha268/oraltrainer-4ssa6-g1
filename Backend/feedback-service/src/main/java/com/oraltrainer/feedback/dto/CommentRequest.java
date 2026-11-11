package com.oraltrainer.feedback.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Corps de POST /api/feedbacks/{id}/comments.
 */
@Schema(description = "Commentaire d'un formateur sur un feedback")
public record CommentRequest(
        @Schema(example = "3") @NotNull Long trainerId,
        @Schema(example = "Travaille la structure de ta conclusion.") @NotBlank @Size(max = 2000) String comment
) {
}
