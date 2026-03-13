package com.edu.politecnicointernacional.sistema_gestion_zoologico.controller;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.AnimalDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoSalud;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoAnimal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.service.AnimalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animales")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService service;

    @GetMapping
    public ResponseEntity<List<AnimalDto>> listarAnimales() {
        return ResponseEntity.ok(service.listarAnimales());
    }

    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<AnimalDto>> listarPorEspecie(@PathVariable String especie) {
        return ResponseEntity.ok(service.listarPorEspecie(especie));
    }

    @GetMapping("/edad/{edad}")
    public ResponseEntity<List<AnimalDto>> listarPorEdad(@PathVariable Integer edad) {
        return ResponseEntity.ok(service.listarPorEdad(edad));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<AnimalDto>> listarPorEstado(@PathVariable EstadoSalud estado) {
        return ResponseEntity.ok(service.listarPorEstado(estado));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<AnimalDto>> listarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(service.listarPorNombre(nombre));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<AnimalDto>> listarPorTipo(@PathVariable TipoAnimal tipo) {
        return ResponseEntity.ok(service.listarPorTipo(tipo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AnimalDto> crearAnimal(@Valid @RequestBody AnimalDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.newAnimal(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AnimalDto> actualizarAnimal(@PathVariable Long id,
                                                      @Valid @RequestBody AnimalDto dto) {
        return ResponseEntity.ok(service.updateAnimal(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAnimal(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
