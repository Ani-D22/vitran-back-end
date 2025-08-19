package com.vitran.backend.dto;

import java.time.LocalDateTime;

/**
 * Single DTO for both create and update.
 * Nullable fields allow partial updates in PATCH.
 */
public record EnumerationDto(
        String enumId,
        String enumTypeId,
        String description,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {}
