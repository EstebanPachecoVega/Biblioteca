package cl.ecomarket.usuario.controller;


import cl.ecomarket.usuario.dto.UsuarioDTO;
import cl.ecomarket.usuario.service.UsuarioService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<UsuarioDTO> listUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> createUser(@Valid @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(service.createUser(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUser(
        @PathVariable Long id,
        @Valid @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(service.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        service.deactivateUser(id);
        return ResponseEntity.noContent().build();
    }
}
