package com.edu.politecnicointernacional.sistema_gestion_zoologico.service.impl;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.CitaMedicaDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Animal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.CitaMedica;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Usuario;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoCita;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.AnimalExceptionValorNoEncontado;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.CitaMedicaExceptionNotFound;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.CitaMedicaExceptionNotNull;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.UsuarioExceptionNotFound;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.mapper.CitaMedicaMapper;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.repository.AnimalRepository;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.repository.CitaMedicaRepository;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.repository.UsuarioRepository;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.service.CitaMedicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CitaMedicaServiceImpl implements CitaMedicaService {

    @Autowired
    private CitaMedicaRepository repository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CitaMedicaMapper mapper;

    @Override
    public List<CitaMedicaDto> listarCitas() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public List<CitaMedicaDto> listarPorEstado(EstadoCita estadoCita) {
        return repository.findByEstadoCita(estadoCita).stream().map(mapper::toDto).toList();
    }

    @Override
    public List<CitaMedicaDto> listarPorAnimal(Long animalId) {
        return repository.findByAnimalId(animalId).stream().map(mapper::toDto).toList();
    }

    @Override
    public List<CitaMedicaDto> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId).stream().map(mapper::toDto).toList();
    }

    @Override
    public List<CitaMedicaDto> listarPorFecha(LocalDate fecha) {
        return repository.findByFecha(fecha).stream().map(mapper::toDto).toList();
    }

    @Override
    public CitaMedicaDto buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new CitaMedicaExceptionNotFound("Cita médica no encontrada con id: " + id));
    }

    @Override
    public CitaMedicaDto crearCita(CitaMedicaDto dto) {
        if (dto == null) {
            throw new CitaMedicaExceptionNotNull("Los datos de la cita no pueden ser nulos");
        }
        Animal animal = animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new AnimalExceptionValorNoEncontado("Animal no encontrado con id: " + dto.getAnimalId()));
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new UsuarioExceptionNotFound("Usuario no encontrado con id: " + dto.getUsuarioId()));
        CitaMedica cita = mapper.toEntity(dto, animal, usuario);
        return mapper.toDto(repository.save(cita));
    }

    @Override
    public CitaMedicaDto actualizarCita(Long id, CitaMedicaDto dto) {
        CitaMedica cita = repository.findById(id)
                .orElseThrow(() -> new CitaMedicaExceptionNotFound("Cita médica no encontrada con id: " + id));
        Animal animal = animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new AnimalExceptionValorNoEncontado("Animal no encontrado con id: " + dto.getAnimalId()));
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new UsuarioExceptionNotFound("Usuario no encontrado con id: " + dto.getUsuarioId()));
        mapper.updateEntityFromDto(dto, cita, animal, usuario);
        return mapper.toDto(repository.save(cita));
    }

    @Override
    public void eliminar(Long id) {
        CitaMedica cita = repository.findById(id)
                .orElseThrow(() -> new CitaMedicaExceptionNotFound("Cita médica no encontrada con id: " + id));
        repository.delete(cita);
    }
}
