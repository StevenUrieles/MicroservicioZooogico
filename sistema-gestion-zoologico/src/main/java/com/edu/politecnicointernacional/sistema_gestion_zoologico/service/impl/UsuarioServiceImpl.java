package com.edu.politecnicointernacional.sistema_gestion_zoologico.service.impl;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.UsuarioDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Usuario;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.UsuarioExceptionEmailDuplicado;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.UsuarioExceptionNotFound;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.exception.UsuarioExceptionNotNull;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.mapper.UsuarioMapper;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.repository.UsuarioRepository;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioMapper mapper;

    @Override
    public List<UsuarioDto> listarUsuarios() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public List<UsuarioDto> listarPorActivo(boolean activo) {
        return repository.findByActivo(activo).stream().map(mapper::toDto).toList();
    }

    @Override
    public UsuarioDto buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new UsuarioExceptionNotFound("Usuario no encontrado con id: " + id));
    }

    @Override
    public UsuarioDto buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDto)
                .orElseThrow(() -> new UsuarioExceptionNotFound("Usuario no encontrado con email: " + email));
    }

    @Override
    public UsuarioDto crearUsuario(UsuarioDto dto) {
        if (dto == null) {
            throw new UsuarioExceptionNotNull("Los datos del usuario no pueden ser nulos");
        }
        if (repository.existsByEmail(dto.getEmail())) {
            throw new UsuarioExceptionEmailDuplicado("Ya existe un usuario con el email: " + dto.getEmail());
        }
        Usuario usuario = mapper.toEntity(dto);
        return mapper.toDto(repository.save(usuario));
    }

    @Override
    public UsuarioDto actualizarUsuario(Long id, UsuarioDto dto) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioExceptionNotFound("Usuario no encontrado con id: " + id));
        // Check email duplicado si cambió
        if (!usuario.getEmail().equals(dto.getEmail()) && repository.existsByEmail(dto.getEmail())) {
            throw new UsuarioExceptionEmailDuplicado("Ya existe un usuario con el email: " + dto.getEmail());
        }
        mapper.updateEntityFromDto(dto, usuario);
        return mapper.toDto(repository.save(usuario));
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioExceptionNotFound("Usuario no encontrado con id: " + id));
        repository.delete(usuario);
    }
}
