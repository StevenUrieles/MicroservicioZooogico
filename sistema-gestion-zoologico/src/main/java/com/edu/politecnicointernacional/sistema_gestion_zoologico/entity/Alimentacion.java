package com.edu.politecnicointernacional.sistema_gestion_zoologico.entity;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoComida;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "alimentacion")
@Getter@Setter
public class Alimentacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoComida tipoComida;

    @NotNull
    @Min(value = 1, message = "Cantidad min. 1kg")
    @Max(value = 100, message = "Cantidad max. 100kg")
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;
}
