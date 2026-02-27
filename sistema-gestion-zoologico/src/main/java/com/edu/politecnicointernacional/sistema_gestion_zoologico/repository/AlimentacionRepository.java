package com.edu.politecnicointernacional.sistema_gestion_zoologico.repository;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.Alimentacion;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoComida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlimentacionRepository extends JpaRepository<Alimentacion, Long> {
    List<Alimentacion> findByTipoComida(TipoComida tipoComida);
    List<Alimentacion> findByAnimalId(Long animalId);
}
