package com.snowhorse.secureBW.user.api;

import com.snowhorse.secureBW.user.entity.Role;
import com.snowhorse.secureBW.user.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Role> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public Role obtener(@PathVariable UUID id) { return service.obtener(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Role crear(@Valid @RequestBody Role role) { return service.crear(role); }

    @PutMapping("/{id}")
    public Role actualizar(@PathVariable UUID id, @Valid @RequestBody Role role) {
        return service.actualizar(id, role);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable UUID id) { service.eliminar(id); }
}