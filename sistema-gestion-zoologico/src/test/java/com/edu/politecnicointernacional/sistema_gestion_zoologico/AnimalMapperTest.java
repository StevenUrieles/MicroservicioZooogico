package com.edu.politecnicointernacional.sistema_gestion_zoologico;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.AnimalDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Animal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoSalud;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoAnimal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.mapper.AnimalMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalMapperTest {

    private AnimalMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new AnimalMapper();
    }

    @Test
    void toDto_debeMapearCorrectamente() {
        Animal animal = new Animal();
        animal.setId(1L);
        animal.setName("Tigre");
        animal.setSpecies("Panthera tigris");
        animal.setAge(3);
        animal.setHealthStatus(EstadoSalud.MEDIA);
        animal.setTypeAnimals(TipoAnimal.FELINO);

        AnimalDto dto = mapper.toDto(animal);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Tigre", dto.getNombre());
        assertEquals("Panthera tigris", dto.getEspecie());
        assertEquals(3, dto.getEdad());
        assertEquals(EstadoSalud.MEDIA, dto.getEstadoSalud());
        assertEquals(TipoAnimal.FELINO, dto.getTipoAnimal());
    }

    @Test
    void toDto_debeRetornarNullCuandoAnimalEsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    void toEntity_debeMapearCorrectamente() {
        AnimalDto dto = new AnimalDto();
        dto.setId(2L);
        dto.setNombre("Boa");
        dto.setEspecie("Boa constrictor");
        dto.setEdad(7);
        dto.setEstadoSalud(EstadoSalud.EXCELENTE);
        dto.setTipoAnimal(TipoAnimal.REPTIL);

        Animal animal = mapper.toEntity(dto);

        assertNotNull(animal);
        assertEquals(2L, animal.getId());
        assertEquals("Boa", animal.getName());
        assertEquals("Boa constrictor", animal.getSpecies());
        assertEquals(7, animal.getAge());
        assertEquals(EstadoSalud.EXCELENTE, animal.getHealthStatus());
        assertEquals(TipoAnimal.REPTIL, animal.getTypeAnimals());
    }

    @Test
    void toEntity_debeRetornarNullCuandoDtoEsNull() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void updateEntityFromDto_debeActualizarCamposCorrectamente() {
        Animal animal = new Animal();
        animal.setName("Viejo nombre");
        animal.setSpecies("Vieja especie");
        animal.setAge(1);

        AnimalDto dto = new AnimalDto();
        dto.setNombre("Nuevo nombre");
        dto.setEspecie("Nueva especie");
        dto.setEdad(5);
        dto.setEstadoSalud(EstadoSalud.MALA);
        dto.setTipoAnimal(TipoAnimal.REPTIL);

        mapper.updateEntityFromDto(dto, animal);

        assertEquals("Nuevo nombre", animal.getName());
        assertEquals("Nueva especie", animal.getSpecies());
        assertEquals(5, animal.getAge());
        assertEquals(EstadoSalud.MALA, animal.getHealthStatus());
    }
}
