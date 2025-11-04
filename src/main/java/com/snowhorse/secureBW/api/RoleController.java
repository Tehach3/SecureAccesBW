package com.snowhorse.secureBW.api;

import com.snowhorse.secureBW.user.entity.Role;
import com.snowhorse.secureBW.user.service.ExternalApiService;
import com.snowhorse.secureBW.user.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService service;
    private final ExternalApiService externalApiService;

    public RoleController(RoleService service, ExternalApiService externalApiService) {
        this.service = service;
        this.externalApiService = externalApiService;
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

    @GetMapping("/external")
    public ResponseEntity<List<Map<String, Object>>> obtenerPostsExternos() {
        var data = externalApiService.obtenerPostsExternos();
        return ResponseEntity.ok(data);
    }
}