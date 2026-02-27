package com.edu.politecnicointernacional.sistema_gestion_zoologico.controller;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.AnimalDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoSalud;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoAnimal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.service.AnimalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/animales")
public class AnimalController{

    @Autowired
    private AnimalService service;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<?> listAnimals(){
        return ResponseEntity.ok(service.listarAnimales());
    }

    @GetMapping("especie/{especie}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> listAnimalsSpecies(@PathVariable String especie){
        List<AnimalDto> list = service.listarPorEspecie(especie);
        return ResponseEntity.ok(list);
    }

    @GetMapping("edad/{edad}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> listAnimalsAge(@PathVariable Integer edad){
        List<AnimalDto> list = service.listarPorEdad(edad);
        return ResponseEntity.ok(list);
    }

    @GetMapping("estado/{estado}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> listAnimalsHealthStatus(@PathVariable EstadoSalud estadoSalud){
        List<AnimalDto> list = service.listarPorEstado(estadoSalud);
        return ResponseEntity.ok(list);
    }
    @GetMapping("nombre/{nombre}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> listAnimalsName(@PathVariable String nombre){
        List<AnimalDto> list = service.listarPorNombre(nombre);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> listAnimalsId(@PathVariable Long id){
        Optional<AnimalDto> dto = Optional.ofNullable(service.buscarPorId(id));
        return ResponseEntity.ok(dto);
    }

    @GetMapping("tipo/{tipo}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> listAnimalsType(@PathVariable TipoAnimal tipoAnimal){
        return ResponseEntity.ok(service.listarPorTipo(tipoAnimal));
    }
    @PostMapping
    @Transactional
    public ResponseEntity<?> newAnimal(@Valid @RequestBody AnimalDto animalDto, BindingResult result){
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->{
                errores.put(err.getField(), "El campo " + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
        }

        AnimalDto nuevo = service.newAnimal(animalDto);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateAnimal(@Validated @PathVariable Long id, @RequestBody AnimalDto animalDto, BindingResult result){
        if(result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();

            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo " + err.getDefaultMessage());
            });

            return ResponseEntity.badRequest().body(errores);
        }
        AnimalDto modificar = service.updateAnimal(id, animalDto);
        return ResponseEntity.ok(modificar);
    }

    @DeleteMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> deleteAnimal(@PathVariable Long id){
        service.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
