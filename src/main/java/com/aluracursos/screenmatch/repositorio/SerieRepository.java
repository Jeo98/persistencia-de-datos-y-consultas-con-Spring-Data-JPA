package com.aluracursos.screenmatch.repositorio;

import com.aluracursos.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    //JpaRepository pide como parametro un tipo de dato generico y un ID haciendo referencia a la posicion de la
    //celda en la base de datos
}
