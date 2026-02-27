package com.edu.politecnicointernacional.sistema_gestion_zoologico.service;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.CitaMedicaDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoCita;

import java.time.LocalDate;
import java.util.List;

public interface CitaMedicaService {
    List<CitaMedicaDto> listarCitas();
    List<CitaMedicaDto> listarPorEstado(EstadoCita estadoCita);
    List<CitaMedicaDto> listarPorAnimal(Long animalId);
    List<CitaMedicaDto> listarPorUsuario(Long usuarioId);
    List<CitaMedicaDto> listarPorFecha(LocalDate fecha);
    CitaMedicaDto buscarPorId(Long id);
    CitaMedicaDto crearCita(CitaMedicaDto dto);
    CitaMedicaDto actualizarCita(Long id, CitaMedicaDto dto);
    void eliminar(Long id);
}
