package com.aluracursos.screenmatch.repositorio;

import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Episodio;
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

    //@Query(value = "SELECT * FROM series WHERE series.total_temporadas <= 6 AND series.evaluacion  >= 7.5",nativeQuery = true)//permite que ejecute QuerySQL

    // En este caso, la consulta es fija, es decir, si el usuario ingresa algun dato no tendra efecto, ya que los parametros estan preestablecidos...
    //El PARAMETRO "SELECT * FROM series WHERE series.total_temporadas <= 6 AND series.evaluacion  >= 7.5" lo consulto previamente en la BD y si funca, lo coloco aqui


    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.evaluacion  >= :evaluacion")//permite que ejecute QuerySQL

        /*Ahora queremos realizar el pedido a base de datos pero con los parametros ingresados por el usuario. Esto se realiza quitando el value,
        remplazando el *  por una s o cualquier letra que representa la Entidad que queremos trabajar, en este caso una Serie, luego despues de FROM y la letra
        ingresamos el nombre de la clase a tratar, Serie. Donde se tenga que utilizar un valor ingresado por el usuario, por ejemplo 'evaluacion', le agregamos
        : antes del nombre para hacer referencia a que utilice el valor enviado por parametro en la invocacion.
    */
    List<Serie> seriesPorTemporadaYEvaluacion( int totalTemporadas, Double evaluacion);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%") //consulta con JPQL
    List<Episodio> episodiosPorNombre(String nombreEpisodio);


    @Query ("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> top5Episodios(Serie serie);
}
