package com.snowhorse.secureBW.api;

import com.snowhorse.secureBW.user.entity.User;
import com.snowhorse.secureBW.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // CRUD
    @GetMapping
    public List<User> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public User obtener(@PathVariable UUID id) { return service.obtener(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User crear(@Valid @RequestBody User user) { return service.crear(user); }

    @PutMapping("/{id}")
    public User actualizar(@PathVariable UUID id, @Valid @RequestBody User user) {
        return service.actualizar(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable UUID id) { service.eliminar(id); }

    // Roles (asignar / quitar)
    record RoleAssignReq(String code, UUID grantedBy) {}
    record RoleRemoveReq(String code) {}

    @PostMapping("/{id}/roles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void asignarRol(@PathVariable UUID id, @RequestBody RoleAssignReq body) {
        service.asignarRol(id, body.code(), body.grantedBy());
    }

    @DeleteMapping("/{id}/roles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void quitarRol(@PathVariable UUID id, @RequestBody RoleRemoveReq body) {
        service.removerRol(id, body.code());
    }

    @GetMapping("/{id}/roles")
    public List<String> rolesDeUsuario(@PathVariable UUID id) {
        return service.rolesDeUsuario(id);
    }
}
