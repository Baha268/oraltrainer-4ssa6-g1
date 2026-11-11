package com.esprit.oral.progress.dto;

import java.util.List;

/** Pagination envelope required by the project REST conventions (Annex A). */
public record PageResponse<T>(List<T> content, int page, int size, long totalElements) {
}
