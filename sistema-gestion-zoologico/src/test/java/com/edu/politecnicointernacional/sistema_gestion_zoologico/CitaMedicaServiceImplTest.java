package com.edu.politecnicointernacional.sistema_gestion_zoologico;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.CitaMedicaDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Animal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.CitaMedica;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Usuario;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoCita;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoSalud;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoAnimal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.AnimalExceptionValorNoEncontado;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.CitaMedicaExceptionNotFound;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.CitaMedicaExceptionNotNull;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.UsuarioExceptionNotFound;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.mapper.CitaMedicaMapper;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.repository.AnimalRepository;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.repository.CitaMedicaRepository;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.repository.UsuarioRepository;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.service.impl.CitaMedicaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitaMedicaServiceImplTest {

    @Mock
    private CitaMedicaRepository repository;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CitaMedicaMapper mapper;

    @InjectMocks
    private CitaMedicaServiceImpl service;

    private Animal animal;
    private Usuario usuario;
    private CitaMedica cita;
    private CitaMedicaDto dto;

    @BeforeEach
    void setUp() {
        animal = new Animal();
        animal.setId(1L);
        animal.setName("León");
        animal.setSpecies("Panthera leo");
        animal.setAge(5);
        animal.setHealthStatus(EstadoSalud.EXCELENTE);
        animal.setTypeAnimals(TipoAnimal.FELINO);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Dr. García");
        usuario.setEmail("garcia@zoo.com");
        usuario.setPassword("password123");
        usuario.setActivo(true);

        cita = new CitaMedica();
        cita.setId(1L);
        cita.setFecha(LocalDate.now().plusDays(1));
        cita.setEstadoCita(EstadoCita.PENDIENTE);
        cita.setAnimal(animal);
        cita.setUsuario(usuario);

        dto = new CitaMedicaDto();
        dto.setId(1L);
        dto.setFecha(LocalDate.now().plusDays(1));
        dto.setEstadoCita(EstadoCita.PENDIENTE);
        dto.setAnimalId(1L);
        dto.setUsuarioId(1L);
    }

    @Test
    void listarCitas_debeRetornarLista() {
        when(repository.findAll()).thenReturn(List.of(cita));
        when(mapper.toDto(cita)).thenReturn(dto);

        List<CitaMedicaDto> result = service.listarCitas();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void listarPorEstado_debeRetornarCitasFiltradas() {
        when(repository.findByEstadoCita(EstadoCita.PENDIENTE)).thenReturn(List.of(cita));
        when(mapper.toDto(cita)).thenReturn(dto);

        List<CitaMedicaDto> result = service.listarPorEstado(EstadoCita.PENDIENTE);

        assertEquals(1, result.size());
    }

    @Test
    void listarPorAnimal_debeRetornarCitasDelAnimal() {
        when(repository.findByAnimalId(1L)).thenReturn(List.of(cita));
        when(mapper.toDto(cita)).thenReturn(dto);

        List<CitaMedicaDto> result = service.listarPorAnimal(1L);

        assertEquals(1, result.size());
    }

    @Test
    void listarPorUsuario_debeRetornarCitasDelUsuario() {
        when(repository.findByUsuarioId(1L)).thenReturn(List.of(cita));
        when(mapper.toDto(cita)).thenReturn(dto);

        List<CitaMedicaDto> result = service.listarPorUsuario(1L);

        assertEquals(1, result.size());
    }

    @Test
    void buscarPorId_debeRetornarCitaExistente() {
        when(repository.findById(1L)).thenReturn(Optional.of(cita));
        when(mapper.toDto(cita)).thenReturn(dto);

        CitaMedicaDto result = service.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(EstadoCita.PENDIENTE, result.getEstadoCita());
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CitaMedicaExceptionNotFound.class, () -> service.buscarPorId(99L));
    }

    @Test
    void crearCita_debeGuardarCorrectamente() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(mapper.toEntity(dto, animal, usuario)).thenReturn(cita);
        when(repository.save(cita)).thenReturn(cita);
        when(mapper.toDto(cita)).thenReturn(dto);

        CitaMedicaDto result = service.crearCita(dto);

        assertNotNull(result);
        verify(repository).save(cita);
    }

    @Test
    void crearCita_debeLanzarExcepcionCuandoDtoEsNulo() {
        assertThrows(CitaMedicaExceptionNotNull.class, () -> service.crearCita(null));
    }

    @Test
    void crearCita_debeLanzarExcepcionCuandoAnimalNoExiste() {
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AnimalExceptionValorNoEncontado.class, () -> service.crearCita(dto));
    }

    @Test
    void crearCita_debeLanzarExcepcionCuandoUsuarioNoExiste() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsuarioExceptionNotFound.class, () -> service.crearCita(dto));
    }

    @Test
    void actualizarCita_debeActualizarCorrectamente() {
        when(repository.findById(1L)).thenReturn(Optional.of(cita));
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.save(cita)).thenReturn(cita);
        when(mapper.toDto(cita)).thenReturn(dto);

        CitaMedicaDto result = service.actualizarCita(1L, dto);

        assertNotNull(result);
        verify(mapper).updateEntityFromDto(dto, cita, animal, usuario);
    }

    @Test
    void actualizarCita_debeLanzarExcepcionCuandoNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CitaMedicaExceptionNotFound.class, () -> service.actualizarCita(99L, dto));
    }

    @Test
    void eliminar_debeEliminarCorrectamente() {
        when(repository.findById(1L)).thenReturn(Optional.of(cita));

        service.eliminar(1L);

        verify(repository).delete(cita);
    }

    @Test
    void eliminar_debeLanzarExcepcionCuandoNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CitaMedicaExceptionNotFound.class, () -> service.eliminar(99L));
    }
}
