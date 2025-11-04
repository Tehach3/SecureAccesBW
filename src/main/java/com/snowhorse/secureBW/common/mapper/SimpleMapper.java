package com.snowhorse.secureBW.common.mapper;

import com.snowhorse.secureBW.api.dto.*;
import com.snowhorse.secureBW.user.entity.*;
import com.snowhorse.secureBW.user.entity.Role;


public final class SimpleMapper {
    private SimpleMapper() {}

    public static RoleViewDTO toRoleViewDTO(Role r) {
        return new RoleViewDTO(
                r.getId(),
                r.getCode(),
                r.getName(),
                r.getDescription(),
                r.getCreatedAt(),
                r.getUpdatedAt()
        );
    }

    public static Role toRoleEntity(RoleCreateDTO dto) {
        Role r = new Role();
        r.setCode(dto.code());
        r.setName(dto.name());
        r.setDescription(dto.description());
        return r;
    }
}