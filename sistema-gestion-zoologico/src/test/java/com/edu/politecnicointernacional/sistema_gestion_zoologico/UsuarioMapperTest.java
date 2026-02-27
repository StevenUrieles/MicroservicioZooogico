package com.edu.politecnicointernacional.sistema_gestion_zoologico;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.UsuarioDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Usuario;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    private UsuarioMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UsuarioMapper();
    }

    @Test
    void toDto_debeMapearCorrectamente() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Ana");
        usuario.setEmail("ana@zoo.com");
        usuario.setPassword("secret123");
        usuario.setActivo(true);

        UsuarioDto dto = mapper.toDto(usuario);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Ana", dto.getNombre());
        assertEquals("ana@zoo.com", dto.getEmail());
        assertEquals("secret123", dto.getPassword());
        assertTrue(dto.getActivo());
    }

    @Test
    void toDto_debeRetornarNullCuandoUsuarioEsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    void toEntity_debeMapearCorrectamente() {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(2L);
        dto.setNombre("Carlos");
        dto.setEmail("carlos@zoo.com");
        dto.setPassword("carlos123");
        dto.setActivo(false);

        Usuario usuario = mapper.toEntity(dto);

        assertNotNull(usuario);
        assertEquals(2L, usuario.getId());
        assertEquals("Carlos", usuario.getNombre());
        assertEquals("carlos@zoo.com", usuario.getEmail());
        assertFalse(usuario.isActivo());
    }

    @Test
    void toEntity_debeRetornarNullCuandoDtoEsNull() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void updateEntityFromDto_debeActualizarCamposCorrectamente() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Viejo nombre");
        usuario.setEmail("viejo@zoo.com");
        usuario.setPassword("oldpass");
        usuario.setActivo(false);

        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Nuevo nombre");
        dto.setEmail("nuevo@zoo.com");
        dto.setPassword("newpass1");
        dto.setActivo(true);

        mapper.updateEntityFromDto(dto, usuario);

        assertEquals("Nuevo nombre", usuario.getNombre());
        assertEquals("nuevo@zoo.com", usuario.getEmail());
        assertEquals("newpass1", usuario.getPassword());
        assertTrue(usuario.isActivo());
    }
}
