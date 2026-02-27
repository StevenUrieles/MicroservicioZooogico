package com.edu.politecnicointernacional.sistema_gestion_zoologico.service;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.UsuarioDto;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDto> listarUsuarios();
    List<UsuarioDto> listarPorActivo(boolean activo);
    UsuarioDto buscarPorId(Long id);
    UsuarioDto buscarPorEmail(String email);
    UsuarioDto crearUsuario(UsuarioDto dto);
    UsuarioDto actualizarUsuario(Long id, UsuarioDto dto);
    void eliminar(Long id);
}
