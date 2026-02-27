package com.edu.politecnicointernacional.sistema_gestion_zoologico.entity;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoSalud;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoAnimal;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede ser nulo o en blanco")
    @Column(name = "nombre", length = 50, nullable = false)
    private String name;

    @NotBlank(message = "el campo Especie es obligatorio")
    @Column(name = "especie", length = 50, nullable = false)
    private String species;

    @NotNull
    @Min(value = 0, message = "La edad minima es de 0")
    @Max(value = 100, message = "La edad maxima es de 100")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El estado salud no puede ser nulo")
    private EstadoSalud healthStatus;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El tipo del animal no puede ser nulo")
    private TipoAnimal typeAnimals;
}
