package com.aluracursos.screenmatch.repositorio;

import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    //JpaRepository pide como parametro un tipo de dato generico y un ID haciendo referencia a la posicion de la
    //celda en la base de datos

    Optional<Serie> findBytituloContainsIgnoreCase(String nombreSerie);
    List<Serie> findTop5ByOrderByEvaluacionDesc();
    List<Serie> findByGenero(Categoria categoria);
    //    List<Serie>findByTotalTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(int totalTemporadas, Double evaluacion);
    // Ahora vamos a hacer lo mismo que la linea de arriba pero aplicando Native Query, es decir, consultando directamente en base de datos
    @Query(value = "SELECT * FROM series WHERE series.total_temporadas <= 6 AND series.evaluacion  >= 7.5",nativeQuery = true)//permite que ejecute QuerySQL
    // En este caso, la consulta es fija, es decir, si el usuario ingresa algun dato no tendra efecto, ya que los parametros estan preestablecidos...
    //El PARAMETRO "SELECT * FROM series WHERE series.total_temporadas <= 6 AND series.evaluacion  >= 7.5" lo consulto previamente en la BD y si funca, lo coloco aqui

    List<Serie> seriesPorTemporadaYEvaluacion( );

}
