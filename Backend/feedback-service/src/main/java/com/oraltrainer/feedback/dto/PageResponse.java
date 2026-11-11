package com.oraltrainer.feedback.dto;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Format de pagination commun du projet : content, page, size, totalElements.
 */
public record PageResponse<T>(List<T> content, int page, int size, long totalElements) {

    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements());
    }
}
