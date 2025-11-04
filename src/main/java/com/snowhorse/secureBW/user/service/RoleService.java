package com.snowhorse.secureBW.user.service;

import com.snowhorse.secureBW.user.entity.Role;
import com.snowhorse.secureBW.user.entity.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RoleService {

    private final RoleRepository repo;

    public RoleService(RoleRepository repo) {
        this.repo = repo;
    }

    // ------- CRUD -------
    public List<Role> listar() {
        return repo.findAll();
    }

    public Role obtener(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
    }

    @Transactional
    public Role crear(Role r) {
        if (repo.existsByCode(r.getCode()))
            throw new IllegalArgumentException("El código de rol ya existe");
        var now = OffsetDateTime.now();
        r.setId(null);
        if (r.getCreatedAt() == null) r.setCreatedAt(now);
        r.setUpdatedAt(now);
        return repo.save(r);
    }

    @Transactional
    public Role actualizar(UUID id, Role in) {
        var r = obtener(id);
        if (!r.getCode().equals(in.getCode()) && repo.existsByCode(in.getCode()))
            throw new IllegalArgumentException("El código de rol ya existe");
        r.setCode(in.getCode());
        r.setName(in.getName());
        r.setDescription(in.getDescription());
        r.setUpdatedAt(OffsetDateTime.now());
        return repo.save(r);
    }

    @Transactional
    public void eliminar(UUID id) {
        repo.delete(obtener(id));
    }
}
