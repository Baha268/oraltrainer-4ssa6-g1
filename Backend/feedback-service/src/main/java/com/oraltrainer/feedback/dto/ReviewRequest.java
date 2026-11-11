package com.oraltrainer.feedback.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Corps de PUT /api/feedbacks/{id}/review.
 * Les scores sont optionnels : s'ils sont tous absents, le feedback est simplement VALIDATED ;
 * si au moins un score est fourni, il passe à ADJUSTED.
 */
@Schema(description = "Validation ou ajustement d'un feedback par un formateur")
public record ReviewRequest(
        @Schema(example = "3") @NotNull Long trainerId,
        @Schema(example = "75.0") @DecimalMin("0.0") @DecimalMax("100.0") Double pronunciation,
        @DecimalMin("0.0") @DecimalMax("100.0") Double fluency,
        @DecimalMin("0.0") @DecimalMax("100.0") Double intonation,
        @DecimalMin("0.0") @DecimalMax("100.0") Double lexicon,
        @Schema(example = "60.0") @DecimalMin("0.0") @DecimalMax("100.0") Double grammar,
        @DecimalMin("0.0") @DecimalMax("100.0") Double coherence,
        @Schema(example = "Bonne prise de parole, attention aux accords.") @Size(max = 2000) String comment
) {
}
