package com.edu.politecnicointernacional.sistema_gestion_zoologico;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.UsuarioDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Usuario;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.UsuarioExceptionEmailDuplicado;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.UsuarioExceptionNotFound;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.UsuarioExceptionNotNull;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.mapper.UsuarioMapper;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.repository.UsuarioRepository;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.service.impl.UsuarioServiceImpl;
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
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private UsuarioMapper mapper;

    @InjectMocks
    private UsuarioServiceImpl service;

    private Usuario usuario;
    private UsuarioDto dto;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Dr. García");
        usuario.setEmail("garcia@zoo.com");
        usuario.setPassword("password123");
        usuario.setActivo(true);

        dto = new UsuarioDto();
        dto.setId(1L);
        dto.setNombre("Dr. García");
        dto.setEmail("garcia@zoo.com");
        dto.setPassword("password123");
        dto.setActivo(true);
    }

    @Test
    void listarUsuarios_debeRetornarLista() {
        when(repository.findAll()).thenReturn(List.of(usuario));
        when(mapper.toDto(usuario)).thenReturn(dto);

        List<UsuarioDto> result = service.listarUsuarios();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void listarPorActivo_debeRetornarUsuariosActivos() {
        when(repository.findByActivo(true)).thenReturn(List.of(usuario));
        when(mapper.toDto(usuario)).thenReturn(dto);

        List<UsuarioDto> result = service.listarPorActivo(true);

        assertEquals(1, result.size());
        assertTrue(result.get(0).getActivo());
    }

    @Test
    void buscarPorId_debeRetornarUsuarioExistente() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(mapper.toDto(usuario)).thenReturn(dto);

        UsuarioDto result = service.buscarPorId(1L);

        assertNotNull(result);
        assertEquals("garcia@zoo.com", result.getEmail());
    }

    @Test
    void buscarPorId_debeLanzarExcepcionCuandoNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioExceptionNotFound.class, () -> service.buscarPorId(99L));
    }

    @Test
    void buscarPorEmail_debeRetornarUsuarioCorrecto() {
        when(repository.findByEmail("garcia@zoo.com")).thenReturn(Optional.of(usuario));
        when(mapper.toDto(usuario)).thenReturn(dto);

        UsuarioDto result = service.buscarPorEmail("garcia@zoo.com");

        assertNotNull(result);
        assertEquals("garcia@zoo.com", result.getEmail());
    }

    @Test
    void buscarPorEmail_debeLanzarExcepcionCuandoNoExiste() {
        when(repository.findByEmail("noexiste@zoo.com")).thenReturn(Optional.empty());

        assertThrows(UsuarioExceptionNotFound.class, () -> service.buscarPorEmail("noexiste@zoo.com"));
    }

    @Test
    void crearUsuario_debeGuardarCorrectamente() {
        when(repository.existsByEmail("garcia@zoo.com")).thenReturn(false);
        when(mapper.toEntity(dto)).thenReturn(usuario);
        when(repository.save(usuario)).thenReturn(usuario);
        when(mapper.toDto(usuario)).thenReturn(dto);

        UsuarioDto result = service.crearUsuario(dto);

        assertNotNull(result);
        verify(repository).save(usuario);
    }

    @Test
    void crearUsuario_debeLanzarExcepcionCuandoDtoEsNulo() {
        assertThrows(UsuarioExceptionNotNull.class, () -> service.crearUsuario(null));
    }

    @Test
    void crearUsuario_debeLanzarExcepcionCuandoEmailDuplicado() {
        when(repository.existsByEmail("garcia@zoo.com")).thenReturn(true);

        assertThrows(UsuarioExceptionEmailDuplicado.class, () -> service.crearUsuario(dto));
    }

    @Test
    void actualizarUsuario_debeActualizarCorrectamente() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(repository.save(usuario)).thenReturn(usuario);
        when(mapper.toDto(usuario)).thenReturn(dto);

        UsuarioDto result = service.actualizarUsuario(1L, dto);

        assertNotNull(result);
        verify(mapper).updateEntityFromDto(dto, usuario);
    }

    @Test
    void actualizarUsuario_debeLanzarExcepcionCuandoNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioExceptionNotFound.class, () -> service.actualizarUsuario(99L, dto));
    }

    @Test
    void actualizarUsuario_debeLanzarExcepcionCuandoEmailDuplicadoEnOtroUsuario() {
        Usuario otroUsuario = new Usuario();
        otroUsuario.setId(1L);
        otroUsuario.setEmail("otro@zoo.com");
        otroUsuario.setNombre("Otro");
        otroUsuario.setPassword("pass1234");
        otroUsuario.setActivo(true);

        when(repository.findById(1L)).thenReturn(Optional.of(otroUsuario));
        when(repository.existsByEmail("garcia@zoo.com")).thenReturn(true);

        assertThrows(UsuarioExceptionEmailDuplicado.class, () -> service.actualizarUsuario(1L, dto));
    }

    @Test
    void eliminar_debeEliminarCorrectamente() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        service.eliminar(1L);

        verify(repository).delete(usuario);
    }

    @Test
    void eliminar_debeLanzarExcepcionCuandoNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioExceptionNotFound.class, () -> service.eliminar(99L));
    }
}
