package com.edu.politecnicointernacional.sistema_gestion_zoologico.repository;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.CitaMedica;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CitaMedicaRepository extends JpaRepository<CitaMedica, Long> {
    List<CitaMedica> findByEstadoCita(EstadoCita estadoCita);
    List<CitaMedica> findByAnimalId(Long animalId);
    List<CitaMedica> findByUsuarioId(Long usuarioId);
    List<CitaMedica> findByFecha(LocalDate fecha);
}
