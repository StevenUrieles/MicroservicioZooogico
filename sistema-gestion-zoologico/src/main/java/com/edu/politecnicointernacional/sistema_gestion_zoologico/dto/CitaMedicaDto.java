package com.edu.politecnicointernacional.sistema_gestion_zoologico.dto;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoCita;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CitaMedicaDto {

    private Long id;

    @FutureOrPresent(message = "La fecha no puede ser pasada")
    @NotNull(message = "La fecha no puede ser nula")
    private LocalDate fecha;

    @NotNull(message = "El estado de la cita no puede ser nulo")
    private EstadoCita estadoCita;

    @NotNull(message = "El animal no puede ser nulo")
    private Long animalId;

    @NotNull(message = "El usuario no puede ser nulo")
    private Long usuarioId;
}
