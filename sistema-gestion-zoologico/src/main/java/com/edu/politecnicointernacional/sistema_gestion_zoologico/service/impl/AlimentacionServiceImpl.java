package com.edu.politecnicointernacional.sistema_gestion_zoologico.service.impl;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.AlimentacionDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Alimentacion;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Animal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoComida;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.AlimentacionExceptionNotFound;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.AlimentacionExceptionNotNull;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.AnimalExceptionValorNoEncontado;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.mapper.AlimentacionMapper;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.repository.AlimentacionRepository;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.repository.AnimalRepository;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.service.AlimentacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlimentacionServiceImpl implements AlimentacionService {

    @Autowired
    private AlimentacionRepository repository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AlimentacionMapper mapper;

    @Override
    public List<AlimentacionDto> listarAlimentaciones() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public List<AlimentacionDto> listarPorTipoComida(TipoComida tipoComida) {
        return repository.findByTipoComida(tipoComida).stream().map(mapper::toDto).toList();
    }

    @Override
    public List<AlimentacionDto> listarPorAnimal(Long animalId) {
        return repository.findByAnimalId(animalId).stream().map(mapper::toDto).toList();
    }

    @Override
    public AlimentacionDto buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new AlimentacionExceptionNotFound("Alimentación no encontrada con id: " + id));
    }

    @Override
    public AlimentacionDto crearAlimentacion(AlimentacionDto dto) {
        if (dto == null) {
            throw new AlimentacionExceptionNotNull("Los datos de alimentación no pueden ser nulos");
        }
        Animal animal = animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new AnimalExceptionValorNoEncontado("Animal no encontrado con id: " + dto.getAnimalId()));
        Alimentacion alimentacion = mapper.toEntity(dto, animal);
        return mapper.toDto(repository.save(alimentacion));
    }

    @Override
    public AlimentacionDto actualizarAlimentacion(Long id, AlimentacionDto dto) {
        Alimentacion alimentacion = repository.findById(id)
                .orElseThrow(() -> new AlimentacionExceptionNotFound("Alimentación no encontrada con id: " + id));
        Animal animal = animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new AnimalExceptionValorNoEncontado("Animal no encontrado con id: " + dto.getAnimalId()));
        mapper.updateEntityFromDto(dto, alimentacion, animal);
        return mapper.toDto(repository.save(alimentacion));
    }

    @Override
    public void eliminar(Long id) {
        Alimentacion alimentacion = repository.findById(id)
                .orElseThrow(() -> new AlimentacionExceptionNotFound("Alimentación no encontrada con id: " + id));
        repository.delete(alimentacion);
    }
}
