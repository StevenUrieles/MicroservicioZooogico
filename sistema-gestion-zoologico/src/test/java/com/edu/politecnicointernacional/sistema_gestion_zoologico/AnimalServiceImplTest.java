package com.edu.politecnicointernacional.sistema_gestion_zoologico;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.AnimalDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Animal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoSalud;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoAnimal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.AnimalExceptionIsPresent;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.AnimalExceptionNotNull;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.AnimalExceptionValorNoEncontado;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.mapper.AnimalMapper;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.repository.AnimalRepository;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.service.impl.AnimalServiceImpl;
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
class AnimalServiceImplTest {

    @Mock
    private AnimalRepository repository;

    @Mock
    private AnimalMapper mapper;

    @InjectMocks
    private AnimalServiceImpl service;

    private Animal animal;
    private AnimalDto animalDto;

    @BeforeEach
    void setUp() {
        animal = new Animal();
        animal.setId(1L);
        animal.setName("León");
        animal.setSpecies("Panthera leo");
        animal.setAge(5);
        animal.setHealthStatus(EstadoSalud.EXCELENTE);
        animal.setTypeAnimals(TipoAnimal.FELINO);

        animalDto = new AnimalDto();
        animalDto.setId(1L);
        animalDto.setNombre("León");
        animalDto.setEspecie("Panthera leo");
        animalDto.setEdad(5);
        animalDto.setEstadoSalud(EstadoSalud.EXCELENTE);
        animalDto.setTipoAnimal(TipoAnimal.FELINO);
    }

    @Test
    void listarAnimales_debeRetornarListaDeAnimales() {
        when(repository.findAll()).thenReturn(List.of(animal));
        when(mapper.toDto(animal)).thenReturn(animalDto);

        List<AnimalDto> result = service.listarAnimales();

        assertEquals(1, result.size());
        assertEquals("León", result.get(0).getNombre());
        verify(repository).findAll();
    }

    @Test
    void listarPorEspecie_debeRetornarAnimalesFiltrados() {
        when(repository.findBySpecies("Panthera leo")).thenReturn(List.of(animal));
        when(mapper.toDto(animal)).thenReturn(animalDto);

        List<AnimalDto> result = service.listarPorEspecie("Panthera leo");

        assertEquals(1, result.size());
        verify(repository).findBySpecies("Panthera leo");
    }

    @Test
    void listarPorEdad_debeRetornarAnimalesPorEdad() {
        when(repository.findByAge(5)).thenReturn(List.of(animal));
        when(mapper.toDto(animal)).thenReturn(animalDto);

        List<AnimalDto> result = service.listarPorEdad(5);

        assertEquals(1, result.size());
        verify(repository).findByAge(5);
    }

    @Test
    void listarPorEstado_debeRetornarAnimalesPorEstadoSalud() {
        when(repository.findByHealthStatus(EstadoSalud.EXCELENTE)).thenReturn(List.of(animal));
        when(mapper.toDto(animal)).thenReturn(animalDto);

        List<AnimalDto> result = service.listarPorEstado(EstadoSalud.EXCELENTE);

        assertEquals(1, result.size());
        verify(repository).findByHealthStatus(EstadoSalud.EXCELENTE);
    }

    @Test
    void listarPorTipo_debeRetornarAnimalesPorTipo() {
        when(repository.findByTypeAnimals(TipoAnimal.FELINO)).thenReturn(List.of(animal));
        when(mapper.toDto(animal)).thenReturn(animalDto);

        List<AnimalDto> result = service.listarPorTipo(TipoAnimal.FELINO);

        assertEquals(1, result.size());
    }

    @Test
    void buscarPorId_debeRetornarAnimalCuandoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.of(animal));
        when(mapper.toDto(animal)).thenReturn(animalDto);

        AnimalDto result = service.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(AnimalExceptionValorNoEncontado.class, () -> service.buscarPorId(99L));
    }

    @Test
    void newAnimal_debeGuardarYRetornarAnimal() {
        when(mapper.toEntity(animalDto)).thenReturn(animal);
        when(repository.save(animal)).thenReturn(animal);
        when(mapper.toDto(animal)).thenReturn(animalDto);

        AnimalDto result = service.newAnimal(animalDto);

        assertNotNull(result);
        verify(repository).save(animal);
    }

    @Test
    void newAnimal_debeLanzarExcepcionCuandoDtoEsNulo() {
        assertThrows(AnimalExceptionNotNull.class, () -> service.newAnimal(null));
    }

    @Test
    void updateAnimal_debeActualizarAnimalExistente() {
        when(repository.findById(1L)).thenReturn(Optional.of(animal));
        when(repository.save(animal)).thenReturn(animal);
        when(mapper.toDto(animal)).thenReturn(animalDto);

        AnimalDto result = service.updateAnimal(1L, animalDto);

        assertNotNull(result);
        verify(mapper).updateEntityFromDto(animalDto, animal);
        verify(repository).save(animal);
    }

    @Test
    void updateAnimal_debeLanzarExcepcionCuandoAnimalNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(AnimalExceptionIsPresent.class, () -> service.updateAnimal(99L, animalDto));
    }

    @Test
    void eliminar_debeEliminarAnimalExistente() {
        when(repository.findById(1L)).thenReturn(Optional.of(animal));

        service.eliminar(1L);

        verify(repository).delete(animal);
    }

    @Test
    void eliminar_debeLanzarExcepcionCuandoAnimalNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(AnimalExceptionIsPresent.class, () -> service.eliminar(99L));
    }
}
