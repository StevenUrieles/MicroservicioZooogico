package com.edu.politecnicointernacional.sistema_gestion_zoologico;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.AlimentacionDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Alimentacion;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Animal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoSalud;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoAnimal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoComida;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.AlimentacionExceptionNotFound;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.AlimentacionExceptionNotNull;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.AnimalExceptionValorNoEncontado;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.mapper.AlimentacionMapper;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.repository.AlimentacionRepository;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.repository.AnimalRepository;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.service.impl.AlimentacionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlimentacionServiceImplTest {

    @Mock
    private AlimentacionRepository repository;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private AlimentacionMapper mapper;

    @InjectMocks
    private AlimentacionServiceImpl service;

    private Animal animal;
    private Alimentacion alimentacion;
    private AlimentacionDto dto;

    @BeforeEach
    void setUp() {
        animal = new Animal();
        animal.setId(1L);
        animal.setName("León");
        animal.setSpecies("Panthera leo");
        animal.setAge(5);
        animal.setHealthStatus(EstadoSalud.EXCELENTE);
        animal.setTypeAnimals(TipoAnimal.FELINO);

        alimentacion = new Alimentacion();
        alimentacion.setId(1L);
        alimentacion.setTipoComida(TipoComida.CARNES);
        alimentacion.setCantidad(10);
        alimentacion.setAnimal(animal);

        dto = new AlimentacionDto();
        dto.setId(1L);
        dto.setTipoComida(TipoComida.CARNES);
        dto.setCantidad(10);
        dto.setAnimalId(1L);
    }

    @Test
    void listarAlimentaciones_debeRetornarLista() {
        when(repository.findAll()).thenReturn(List.of(alimentacion));
        when(mapper.toDto(alimentacion)).thenReturn(dto);

        List<AlimentacionDto> result = service.listarAlimentaciones();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void listarPorTipoComida_debeRetornarFiltrado() {
        when(repository.findByTipoComida(TipoComida.CARNES)).thenReturn(List.of(alimentacion));
        when(mapper.toDto(alimentacion)).thenReturn(dto);

        List<AlimentacionDto> result = service.listarPorTipoComida(TipoComida.CARNES);

        assertEquals(1, result.size());
    }

    @Test
    void listarPorAnimal_debeRetornarAlimentacionesDelAnimal() {
        when(repository.findByAnimalId(1L)).thenReturn(List.of(alimentacion));
        when(mapper.toDto(alimentacion)).thenReturn(dto);

        List<AlimentacionDto> result = service.listarPorAnimal(1L);

        assertEquals(1, result.size());
    }

    @Test
    void buscarPorId_debeRetornarAlimentacionExistente() {
        when(repository.findById(1L)).thenReturn(Optional.of(alimentacion));
        when(mapper.toDto(alimentacion)).thenReturn(dto);

        AlimentacionDto result = service.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(TipoComida.CARNES, result.getTipoComida());
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(AlimentacionExceptionNotFound.class, () -> service.buscarPorId(99L));
    }

    @Test
    void crearAlimentacion_debeGuardarCorrectamente() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(mapper.toEntity(dto, animal)).thenReturn(alimentacion);
        when(repository.save(alimentacion)).thenReturn(alimentacion);
        when(mapper.toDto(alimentacion)).thenReturn(dto);

        AlimentacionDto result = service.crearAlimentacion(dto);

        assertNotNull(result);
        verify(repository).save(alimentacion);
    }

    @Test
    void crearAlimentacion_debeLanzarExcepcionCuandoDtoEsNulo() {
        assertThrows(AlimentacionExceptionNotNull.class, () -> service.crearAlimentacion(null));
    }

    @Test
    void crearAlimentacion_debeLanzarExcepcionCuandoAnimalNoExiste() {
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AnimalExceptionValorNoEncontado.class, () -> service.crearAlimentacion(dto));
    }

    @Test
    void actualizarAlimentacion_debeActualizarCorrectamente() {
        when(repository.findById(1L)).thenReturn(Optional.of(alimentacion));
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(repository.save(alimentacion)).thenReturn(alimentacion);
        when(mapper.toDto(alimentacion)).thenReturn(dto);

        AlimentacionDto result = service.actualizarAlimentacion(1L, dto);

        assertNotNull(result);
        verify(mapper).updateEntityFromDto(dto, alimentacion, animal);
    }

    @Test
    void actualizarAlimentacion_debeLanzarExcepcionCuandoNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(AlimentacionExceptionNotFound.class, () -> service.actualizarAlimentacion(99L, dto));
    }

    @Test
    void eliminar_debeEliminarCorrectamente() {
        when(repository.findById(1L)).thenReturn(Optional.of(alimentacion));

        service.eliminar(1L);

        verify(repository).delete(alimentacion);
    }

    @Test
    void eliminar_debeLanzarExcepcionCuandoNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(AlimentacionExceptionNotFound.class, () -> service.eliminar(99L));
    }
}
