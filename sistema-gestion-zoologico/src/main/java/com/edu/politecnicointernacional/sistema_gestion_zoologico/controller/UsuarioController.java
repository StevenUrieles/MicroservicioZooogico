package com.edu.politecnicointernacional.sistema_gestion_zoologico.controller;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.UsuarioDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<?> listarUsuarios() {
        return ResponseEntity.ok(service.listarUsuarios());
    }

    @GetMapping("/activo/{activo}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> listarPorActivo(@PathVariable boolean activo) {
        return ResponseEntity.ok(service.listarPorActivo(activo));
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/email/{email}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.buscarPorEmail(email));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioDto dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->
                    errores.put(err.getField(), "El campo " + err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearUsuario(dto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id,
                                               @Valid @RequestBody UsuarioDto dto,
                                               BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->
                    errores.put(err.getField(), "El campo " + err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok(service.actualizarUsuario(id, dto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
