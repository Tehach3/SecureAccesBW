package com.snowhorse.secureBW.api.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record RoleViewDTO(
        UUID id,
        String code,
        String name,
        String description,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
