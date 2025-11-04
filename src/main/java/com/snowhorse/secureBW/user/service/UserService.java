package com.snowhorse.secureBW.user.service;

import com.snowhorse.secureBW.user.entity.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final UserRoleRepository userRoleRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepo, RoleRepository roleRepo, UserRoleRepository userRoleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.userRoleRepo = userRoleRepo;
    }

    public List<User> listar() {
        return userRepo.findAll();
    }

    public User obtener(UUID id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    @Transactional
    public User crear(User u) {
        if (userRepo.existsByUsername(u.getUsername()))
            throw new IllegalArgumentException("El nombre de usuario ya existe");

        u.setId(null);
        u.setActive(true);
        u.setFailedAttempts(0);
        u.setCreatedAt(OffsetDateTime.now());
        u.setUpdatedAt(OffsetDateTime.now());

        if (u.getGpgFingerprint() != null && !u.getGpgFingerprint().isBlank()) {
            u.setGpgFingerprint(u.getGpgFingerprint().trim());
        }

        return userRepo.save(u);
    }

    @Transactional
    public User actualizar(UUID id, User datos) {
        var u = obtener(id);
        u.setUsername(datos.getUsername());
        u.setActive(datos.getActive());
        u.setFailedAttempts(datos.getFailedAttempts());
        u.setLockedUntil(datos.getLockedUntil());
        u.setLastLoginAt(datos.getLastLoginAt());
        u.setUpdatedAt(OffsetDateTime.now());
        return userRepo.save(u);
    }

    @Transactional
    public void eliminar(UUID id) {
        userRepo.delete(obtener(id));
    }

    @Transactional
    public void asignarRol(UUID userId, String roleCode, UUID grantedBy) {
        var user = obtener(userId);
        var role = roleRepo.findByCode(roleCode)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + roleCode));

        var id = new UserRoleID(user.getId(), role.getId());
        if (userRoleRepo.existsById_UserIdAndId_RoleId(user.getId(), role.getId()))
            return;

        var link = UserRole.builder()
                .id(id)
                .user(user)
                .role(role)
                .grantedAt(OffsetDateTime.now())
                .grantedBy(grantedBy)
                .build();

        user.getUserRoles().add(link);
        userRoleRepo.save(link);
    }

    @Transactional
    public void removerRol(UUID userId, String roleCode) {
        var user = obtener(userId);
        var role = roleRepo.findByCode(roleCode)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + roleCode));
        userRoleRepo.deleteById_UserIdAndId_RoleId(user.getId(), role.getId());
    }

    public List<String> rolesDeUsuario(UUID userId) {
        return userRoleRepo.findRoleCodesByUserId(userId);
    }
}
