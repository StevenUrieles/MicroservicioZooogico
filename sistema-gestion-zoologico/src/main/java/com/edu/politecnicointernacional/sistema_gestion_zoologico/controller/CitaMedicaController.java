package com.edu.politecnicointernacional.sistema_gestion_zoologico.controller;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.CitaMedicaDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoCita;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.service.CitaMedicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
public class CitaMedicaController {

    private final CitaMedicaService service;

    @GetMapping
    public ResponseEntity<List<CitaMedicaDto>> listarCitas() {
        return ResponseEntity.ok(service.listarCitas());
    }

    @GetMapping("/estado/{estadoCita}")
    public ResponseEntity<List<CitaMedicaDto>> listarPorEstado(@PathVariable EstadoCita estadoCita) {
        return ResponseEntity.ok(service.listarPorEstado(estadoCita));
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<CitaMedicaDto>> listarPorAnimal(@PathVariable Long animalId) {
        return ResponseEntity.ok(service.listarPorAnimal(animalId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CitaMedicaDto>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<CitaMedicaDto>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(service.listarPorFecha(fecha));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaMedicaDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<CitaMedicaDto> crearCita(@Valid @RequestBody CitaMedicaDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearCita(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaMedicaDto> actualizarCita(@PathVariable Long id,
                                                        @Valid @RequestBody CitaMedicaDto dto) {
        return ResponseEntity.ok(service.actualizarCita(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
