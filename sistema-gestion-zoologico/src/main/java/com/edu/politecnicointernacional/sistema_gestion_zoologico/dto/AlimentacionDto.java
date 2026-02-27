package com.edu.politecnicointernacional.sistema_gestion_zoologico.dto;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoComida;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlimentacionDto {

    private Long id;

    @NotNull(message = "El tipo de comida no puede ser nulo")
    private TipoComida tipoComida;

    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 1, message = "Cantidad mínima 1kg")
    @Max(value = 100, message = "Cantidad máxima 100kg")
    private Integer cantidad;

    @NotNull(message = "El animal no puede ser nulo")
    private Long animalId;
}
