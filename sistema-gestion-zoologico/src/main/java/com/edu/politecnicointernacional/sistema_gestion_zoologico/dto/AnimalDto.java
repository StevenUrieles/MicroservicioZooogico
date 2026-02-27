package com.edu.politecnicointernacional.sistema_gestion_zoologico.dto;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoSalud;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoAnimal;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AnimalDto {
    private Long id;

    @NotBlank(message = "El nombre no puede ser nulo o en blanco")
    private String nombre;

    @NotBlank(message = "El campo especie es obligatorio")
    private String especie;

    @NotNull(message = "La edad no puede ser nula")
    @Min(value = 0, message = "La edad mínima es 0")
    @Max(value = 100, message = "La edad máxima es 100")
    private Integer edad;

    @NotNull(message = "El estado salud no puede ser nulo")
    private EstadoSalud estadoSalud;

    @NotNull(message = "El tipo del animal no puede ser nulo")
    private TipoAnimal tipoAnimal;
}
