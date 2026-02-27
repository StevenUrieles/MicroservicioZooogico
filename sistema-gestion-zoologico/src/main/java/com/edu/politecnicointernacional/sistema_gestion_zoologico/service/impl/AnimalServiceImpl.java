package com.edu.politecnicointernacional.sistema_gestion_zoologico.service.impl;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.AnimalDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Animal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoSalud;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoAnimal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.AnimalExceptionIsPresent;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.AnimalExceptionNotNull;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.AnimalExceptionValorNoEncontado;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.mapper.AnimalMapper;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.repository.AnimalRepository;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalRepository repository;

    @Autowired
    private AnimalMapper mapper;

    @Override
    public List<AnimalDto> listarAnimales() {
        return repository.findAll()
                .stream()
                .map(mapper :: toDto)
                .toList();
    }

    @Override
    public List<AnimalDto> listarPorEspecie(String especie) {
        return repository.findBySpecies(especie)
                .stream()
                .map(mapper :: toDto)
                .toList();
    }

    @Override
    public List<AnimalDto> listarPorEdad(Integer edad) {
        return repository.findByAge(edad)
                .stream()
                .map(mapper :: toDto)
                .toList();
    }

    @Override
    public List<AnimalDto> listarPorEstado(EstadoSalud estadoSalud) {
        return repository.findByHealthStatus(estadoSalud)
                .stream()
                .map(mapper :: toDto)
                .toList();
    }

    @Override
    public List<AnimalDto> listarPorNombre(String nombre) {
        return repository.findByName(nombre)
                .stream()
                .map(mapper :: toDto)
                .toList();
    }

    @Override
    public List<AnimalDto> listarPorTipo(TipoAnimal tipoAnimal) {
        return repository.findByTypeAnimals(tipoAnimal)
                .stream()
                .map(mapper :: toDto)
                .toList();
    }

    @Override
    public AnimalDto buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper :: toDto)
                .orElseThrow(() -> new AnimalExceptionValorNoEncontado("El animal no fue encontrado"));
    }

    @Override
    public AnimalDto newAnimal(AnimalDto dto) {
        if(dto == null){
            throw new AnimalExceptionNotNull("los valores no pueden ser nulos");
        }
        Animal animal = mapper.toEntity(dto);
        Animal save = repository.save(animal);
        return mapper.toDto(save);
    }

    @Override
    public AnimalDto updateAnimal(Long id, AnimalDto dto) {
        Animal animalActualizado = repository.findById(id)
                .orElseThrow(() -> new AnimalExceptionIsPresent("el animal no esta presente"));

        mapper.updateEntityFromDto(dto, animalActualizado);
        Animal actualizado = repository.save(animalActualizado);
        return mapper.toDto(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        Animal delete = repository.findById(id)
                .orElseThrow(() -> new AnimalExceptionIsPresent("El animal no está presente"));

        repository.delete(delete);
    }
}
