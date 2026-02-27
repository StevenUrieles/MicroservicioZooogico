package com.edu.politecnicointernacional.sistema_gestion_zoologico.mapper;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.AnimalDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Animal;
import org.springframework.stereotype.Component;

@Component
public class AnimalMapper {

    public AnimalDto toDto(Animal animal) {
        if (animal == null) return null;

        AnimalDto dto = new AnimalDto();
        dto.setId(animal.getId());
        dto.setNombre(animal.getName());
        dto.setEspecie(animal.getSpecies());
        dto.setEdad(animal.getAge());
        dto.setEstadoSalud(animal.getHealthStatus());
        dto.setTipoAnimal(animal.getTypeAnimals());
        return dto;
    }

    public Animal toEntity(AnimalDto dto) {
        if (dto == null) return null;

        Animal animal = new Animal();
        animal.setId(dto.getId());
        animal.setName(dto.getNombre());
        animal.setSpecies(dto.getEspecie());
        animal.setAge(dto.getEdad());
        animal.setHealthStatus(dto.getEstadoSalud());
        animal.setTypeAnimals(dto.getTipoAnimal());
        return animal;
    }

    public void updateEntityFromDto(AnimalDto dto, Animal animal) {

        if (dto == null || animal == null) return;

        animal.setName(dto.getNombre());
        animal.setSpecies(dto.getEspecie());
        animal.setAge(dto.getEdad());
        animal.setHealthStatus(dto.getEstadoSalud());
        animal.setTypeAnimals(dto.getTipoAnimal());
    }
}

