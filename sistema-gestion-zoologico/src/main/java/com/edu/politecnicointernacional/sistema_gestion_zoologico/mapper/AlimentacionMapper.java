package com.edu.politecnicointernacional.sistema_gestion_zoologico.mapper;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.AlimentacionDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Alimentacion;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Animal;
import org.springframework.stereotype.Component;

@Component
public class AlimentacionMapper {

    public AlimentacionDto toDto(Alimentacion alimentacion) {
        if (alimentacion == null) return null;
        AlimentacionDto dto = new AlimentacionDto();
        dto.setId(alimentacion.getId());
        dto.setTipoComida(alimentacion.getTipoComida());
        dto.setCantidad(alimentacion.getCantidad());
        dto.setAnimalId(alimentacion.getAnimal() != null ? alimentacion.getAnimal().getId() : null);
        return dto;
    }

    public Alimentacion toEntity(AlimentacionDto dto, Animal animal) {
        if (dto == null) return null;
        Alimentacion alimentacion = new Alimentacion();
        alimentacion.setId(dto.getId());
        alimentacion.setTipoComida(dto.getTipoComida());
        alimentacion.setCantidad(dto.getCantidad());
        alimentacion.setAnimal(animal);
        return alimentacion;
    }

    public void updateEntityFromDto(AlimentacionDto dto, Alimentacion alimentacion, Animal animal) {
        if (dto == null || alimentacion == null) return;
        alimentacion.setTipoComida(dto.getTipoComida());
        alimentacion.setCantidad(dto.getCantidad());
        alimentacion.setAnimal(animal);
    }
}
