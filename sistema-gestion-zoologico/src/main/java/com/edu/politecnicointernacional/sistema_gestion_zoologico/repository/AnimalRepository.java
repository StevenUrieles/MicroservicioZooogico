package com.edu.politecnicointernacional.sistema_gestion_zoologico.repository;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Animal;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoSalud;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoAnimal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal,Long> {
    List<Animal> findBySpecies(String especie);
    List<Animal> findByAge(Integer edad);
    List<Animal> findByHealthStatus(EstadoSalud estadoSalud);
    List<Animal> findByTypeAnimals(TipoAnimal tipoAnimal);
    List<Animal> findByName(String nombre);
    Optional<Animal> findById(Long id);
}
