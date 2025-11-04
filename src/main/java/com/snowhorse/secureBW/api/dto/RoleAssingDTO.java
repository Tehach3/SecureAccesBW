package com.snowhorse.secureBW.api.dto;

import java.util.UUID;
public record RoleAssingDTO(String code, UUID grantedBy) {
}
