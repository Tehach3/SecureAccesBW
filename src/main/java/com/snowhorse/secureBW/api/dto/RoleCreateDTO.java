package com.snowhorse.secureBW.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoleCreateDTO(
        @NotBlank @Size(max=64) String code,
        @NotBlank @Size(max=128) String name,
        String description){
}
