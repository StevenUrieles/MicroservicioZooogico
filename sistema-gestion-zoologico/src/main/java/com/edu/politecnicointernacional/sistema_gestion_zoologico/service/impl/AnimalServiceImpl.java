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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository repository;
    private final AnimalMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<AnimalDto> listarAnimales() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnimalDto> listarPorEspecie(String especie) {
        return repository.findBySpecies(especie).stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnimalDto> listarPorEdad(Integer edad) {
        return repository.findByAge(edad).stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnimalDto> listarPorEstado(EstadoSalud estadoSalud) {
        return repository.findByHealthStatus(estadoSalud).stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnimalDto> listarPorNombre(String nombre) {
        return repository.findByName(nombre).stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnimalDto> listarPorTipo(TipoAnimal tipoAnimal) {
        return repository.findByTypeAnimals(tipoAnimal).stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AnimalDto buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new AnimalExceptionValorNoEncontado("Animal no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public AnimalDto newAnimal(AnimalDto dto) {
        if (dto == null) {
            throw new AnimalExceptionNotNull("Los datos del animal no pueden ser nulos");
        }
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public AnimalDto updateAnimal(Long id, AnimalDto dto) {
        Animal animal = repository.findById(id)
                .orElseThrow(() -> new AnimalExceptionIsPresent("Animal no encontrado con id: " + id));
        mapper.updateEntityFromDto(dto, animal);
        return mapper.toDto(repository.save(animal));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Animal animal = repository.findById(id)
                .orElseThrow(() -> new AnimalExceptionIsPresent("Animal no encontrado con id: " + id));
        repository.delete(animal);
    }
}
