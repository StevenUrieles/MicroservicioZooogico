package com.edu.politecnicointernacional.sistema_gestion_zoologico.entity;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoCita;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "cita_medica")
@Getter@Setter
public class CitaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FutureOrPresent(message = "La fecha no puede ser pasada")
    @NotNull
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EstadoCita estadoCita;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
