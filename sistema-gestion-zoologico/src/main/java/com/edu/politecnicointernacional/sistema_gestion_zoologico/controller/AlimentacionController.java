package com.edu.politecnicointernacional.sistema_gestion_zoologico.controller;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.AlimentacionDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoComida;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.service.AlimentacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/alimentaciones")
public class AlimentacionController {

    @Autowired
    private AlimentacionService service;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<?> listarAlimentaciones() {
        return ResponseEntity.ok(service.listarAlimentaciones());
    }

    @GetMapping("/tipo/{tipoComida}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> listarPorTipo(@PathVariable TipoComida tipoComida) {
        return ResponseEntity.ok(service.listarPorTipoComida(tipoComida));
    }

    @GetMapping("/animal/{animalId}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> listarPorAnimal(@PathVariable Long animalId) {
        return ResponseEntity.ok(service.listarPorAnimal(animalId));
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> crearAlimentacion(@Valid @RequestBody AlimentacionDto dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->
                    errores.put(err.getField(), "El campo " + err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearAlimentacion(dto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizarAlimentacion(@PathVariable Long id, @Valid @RequestBody AlimentacionDto dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->
                    errores.put(err.getField(), "El campo " + err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok(service.actualizarAlimentacion(id, dto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
