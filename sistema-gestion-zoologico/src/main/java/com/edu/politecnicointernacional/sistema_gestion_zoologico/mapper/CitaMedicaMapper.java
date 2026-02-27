package com.edu.politecnicointernacional.sistema_gestion_zoologico.mapper;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.CitaMedicaDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Animal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.CitaMedica;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class CitaMedicaMapper {

    public CitaMedicaDto toDto(CitaMedica cita) {
        if (cita == null) return null;
        CitaMedicaDto dto = new CitaMedicaDto();
        dto.setId(cita.getId());
        dto.setFecha(cita.getFecha());
        dto.setEstadoCita(cita.getEstadoCita());
        dto.setAnimalId(cita.getAnimal() != null ? cita.getAnimal().getId() : null);
        dto.setUsuarioId(cita.getUsuario() != null ? cita.getUsuario().getId() : null);
        return dto;
    }

    public CitaMedica toEntity(CitaMedicaDto dto, Animal animal, Usuario usuario) {
        if (dto == null) return null;
        CitaMedica cita = new CitaMedica();
        cita.setId(dto.getId());
        cita.setFecha(dto.getFecha());
        cita.setEstadoCita(dto.getEstadoCita());
        cita.setAnimal(animal);
        cita.setUsuario(usuario);
        return cita;
    }

    public void updateEntityFromDto(CitaMedicaDto dto, CitaMedica cita, Animal animal, Usuario usuario) {
        if (dto == null || cita == null) return;
        cita.setFecha(dto.getFecha());
        cita.setEstadoCita(dto.getEstadoCita());
        cita.setAnimal(animal);
        cita.setUsuario(usuario);
    }
}
