package com.edu.politecnicointernacional.sistema_gestion_zoologico.controller;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.CitaMedicaDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoCita;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.service.CitaMedicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/citas")
public class CitaMedicaController {

    @Autowired
    private CitaMedicaService service;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<?> listarCitas() {
        return ResponseEntity.ok(service.listarCitas());
    }

    @GetMapping("/estado/{estadoCita}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> listarPorEstado(@PathVariable EstadoCita estadoCita) {
        return ResponseEntity.ok(service.listarPorEstado(estadoCita));
    }

    @GetMapping("/animal/{animalId}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> listarPorAnimal(@PathVariable Long animalId) {
        return ResponseEntity.ok(service.listarPorAnimal(animalId));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @GetMapping("/fecha/{fecha}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(service.listarPorFecha(fecha));
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> crearCita(@Valid @RequestBody CitaMedicaDto dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->
                    errores.put(err.getField(), "El campo " + err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearCita(dto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizarCita(@PathVariable Long id,
                                            @Valid @RequestBody CitaMedicaDto dto,
                                            BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->
                    errores.put(err.getField(), "El campo " + err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok(service.actualizarCita(id, dto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
