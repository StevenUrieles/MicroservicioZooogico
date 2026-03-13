package com.edu.politecnicointernacional.sistema_gestion_zoologico.controller;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.AlimentacionDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoComida;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.service.AlimentacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alimentaciones")
@RequiredArgsConstructor
public class AlimentacionController {

    private final AlimentacionService service;

    @GetMapping
    public ResponseEntity<List<AlimentacionDto>> listarAlimentaciones() {
        return ResponseEntity.ok(service.listarAlimentaciones());
    }

    @GetMapping("/tipo/{tipoComida}")
    public ResponseEntity<List<AlimentacionDto>> listarPorTipo(@PathVariable TipoComida tipoComida) {
        return ResponseEntity.ok(service.listarPorTipoComida(tipoComida));
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<AlimentacionDto>> listarPorAnimal(@PathVariable Long animalId) {
        return ResponseEntity.ok(service.listarPorAnimal(animalId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlimentacionDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUIDADOR')")
    @PostMapping
    public ResponseEntity<AlimentacionDto> crearAlimentacion(@Valid @RequestBody AlimentacionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearAlimentacion(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUIDADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<AlimentacionDto> actualizarAlimentacion(@PathVariable Long id,
                                                                  @Valid @RequestBody AlimentacionDto dto) {
        return ResponseEntity.ok(service.actualizarAlimentacion(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
