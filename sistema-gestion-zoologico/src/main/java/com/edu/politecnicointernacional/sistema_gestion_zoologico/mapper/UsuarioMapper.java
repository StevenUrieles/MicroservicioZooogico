package com.edu.politecnicointernacional.sistema_gestion_zoologico.mapper;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.UsuarioDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDto toDto(Usuario usuario) {
        if (usuario == null) return null;
        UsuarioDto dto = new UsuarioDto();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setPassword(usuario.getPassword());
        dto.setActivo(usuario.isActivo());
        return dto;
    }

    public Usuario toEntity(UsuarioDto dto) {
        if (dto == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setActivo(dto.getActivo());
        return usuario;
    }

    public void updateEntityFromDto(UsuarioDto dto, Usuario usuario) {
        if (dto == null || usuario == null) return;
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setActivo(dto.getActivo());
    }
}
