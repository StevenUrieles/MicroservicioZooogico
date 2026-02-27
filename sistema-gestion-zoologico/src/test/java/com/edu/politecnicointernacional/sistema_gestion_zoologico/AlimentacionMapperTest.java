package com.edu.politecnicointernacional.sistema_gestion_zoologico;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.AlimentacionDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Alimentacion;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Animal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoSalud;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoAnimal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoComida;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.mapper.AlimentacionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlimentacionMapperTest {

    private AlimentacionMapper mapper;
    private Animal animal;

    @BeforeEach
    void setUp() {
        mapper = new AlimentacionMapper();
        animal = new Animal();
        animal.setId(1L);
        animal.setName("León");
        animal.setSpecies("Panthera leo");
        animal.setAge(5);
        animal.setHealthStatus(EstadoSalud.EXCELENTE);
        animal.setTypeAnimals(TipoAnimal.FELINO);
    }

    @Test
    void toDto_debeMapearCorrectamente() {
        Alimentacion alimentacion = new Alimentacion();
        alimentacion.setId(1L);
        alimentacion.setTipoComida(TipoComida.CARNES);
        alimentacion.setCantidad(15);
        alimentacion.setAnimal(animal);

        AlimentacionDto dto = mapper.toDto(alimentacion);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(TipoComida.CARNES, dto.getTipoComida());
        assertEquals(15, dto.getCantidad());
        assertEquals(1L, dto.getAnimalId());
    }

    @Test
    void toDto_debeRetornarNullCuandoAlimentacionEsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    void toEntity_debeMapearCorrectamente() {
        AlimentacionDto dto = new AlimentacionDto();
        dto.setId(1L);
        dto.setTipoComida(TipoComida.PLANTAS);
        dto.setCantidad(5);
        dto.setAnimalId(1L);

        Alimentacion alimentacion = mapper.toEntity(dto, animal);

        assertNotNull(alimentacion);
        assertEquals(TipoComida.PLANTAS, alimentacion.getTipoComida());
        assertEquals(5, alimentacion.getCantidad());
        assertEquals(animal, alimentacion.getAnimal());
    }

    @Test
    void toEntity_debeRetornarNullCuandoDtoEsNull() {
        assertNull(mapper.toEntity(null, animal));
    }

    @Test
    void updateEntityFromDto_debeActualizarCamposCorrectamente() {
        Alimentacion alimentacion = new Alimentacion();
        alimentacion.setTipoComida(TipoComida.CARNES);
        alimentacion.setCantidad(5);

        AlimentacionDto dto = new AlimentacionDto();
        dto.setTipoComida(TipoComida.PLANTAS);
        dto.setCantidad(20);
        dto.setAnimalId(1L);

        mapper.updateEntityFromDto(dto, alimentacion, animal);

        assertEquals(TipoComida.PLANTAS, alimentacion.getTipoComida());
        assertEquals(20, alimentacion.getCantidad());
        assertEquals(animal, alimentacion.getAnimal());
    }
}
