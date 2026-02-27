package com.edu.politecnicointernacional.sistema_gestion_zoologico.service;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.AlimentacionDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoComida;

import java.util.List;

public interface AlimentacionService {
    List<AlimentacionDto> listarAlimentaciones();
    List<AlimentacionDto> listarPorTipoComida(TipoComida tipoComida);
    List<AlimentacionDto> listarPorAnimal(Long animalId);
    AlimentacionDto buscarPorId(Long id);
    AlimentacionDto crearAlimentacion(AlimentacionDto dto);
    AlimentacionDto actualizarAlimentacion(Long id, AlimentacionDto dto);
    void eliminar(Long id);
}
