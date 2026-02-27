package com.edu.politecnicointernacional.sistema_gestion_zoologico.service;

import com.edu.politecnicointernacional.sistema_gestion_zoologico.dto.AnimalDto;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.EstadoSalud;
import com.edu.politecnicointernacional.sistema_gestion_zoologico.entity.enumeradores.TipoAnimal;

import java.util.List;

public interface AnimalService {

    List<AnimalDto> listarAnimales();
    List<AnimalDto> listarPorEspecie(String especie);
    List<AnimalDto> listarPorEdad(Integer edad);
    List<AnimalDto> listarPorEstado(EstadoSalud estadoSalud);
    List<AnimalDto> listarPorNombre(String nombre);
    List<AnimalDto> listarPorTipo(TipoAnimal tipoAnimal);
    AnimalDto buscarPorId(Long id);
    AnimalDto newAnimal(AnimalDto dto);
    AnimalDto updateAnimal(Long id,AnimalDto dto);
    void eliminar(Long id);

}
