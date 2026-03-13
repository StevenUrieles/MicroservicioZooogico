package com.edu.politecnicointernacional.sistema_gestion_zoologico.entity;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", length = 50)
    private String nombre;

    @Email(message = "falta el @")
    @NotBlank(message = "Este campo es obligatorio")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max =  20, message = "8 a 20 caracteres")
    private String password;

    @NotNull(message = "Este campo es obligatorio")
    private boolean activo;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El valor no puede ser nulo")
    private Rol rol;


    @OneToMany(mappedBy = "usuario")
    private List<CitaMedica> citasMedicas;
}
