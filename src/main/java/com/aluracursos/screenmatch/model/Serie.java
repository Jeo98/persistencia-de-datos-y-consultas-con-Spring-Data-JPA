package com.aluracursos.screenmatch.model;

import com.aluracursos.screenmatch.service.ConsultaCHATGPTAPI;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import static com.aluracursos.screenmatch.model.Categoria.*;
@Entity //esto indica que "Series" va a ser convertido en una tabla
@Table(name = "series") // nombre de  la tabla de la base de datos


public class Serie {

    @Id //borro la parte de jakarta.persistence
    @GeneratedValue(strategy = GenerationType.IDENTITY) // genera la estrategia que va a generar automaticamente la IDENTIDAD del Id
    private Long Id;

    @Column(unique = true) // para que no se repita valores de titulo en la base de datos
    private String titulo;
    private Integer totalTemporadas;
    private Double evaluacion;
    private String poster;
    @Enumerated(EnumType.STRING) // utilizo Enumerated por el tipo Enum de genero(enumeracion)
    //pero en este caso utilizo String por si se modifica el Enum, evitando posibles problemas a futuro
    private Categoria genero;
    private String actores;
    private String sinopsis;
    //el siguiente comando es para decirle a la base de datos que ignore el dato
    //@Transient
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch=FetchType.EAGER )   //mapea la relacion serie episodio por medidio del atributo serie

    private List<Episodio> episodios;

    public Serie(){
        //todas las clases deben tener constructor predeterminado
    }
    public Serie(DatosSerie datosSerie){
            this.titulo= datosSerie.titulo();
            this.totalTemporadas= datosSerie.totalTemporadas();
            this.evaluacion= OptionalDouble.of(Double.valueOf(datosSerie.evaluacion())).orElse(0);
            this.actores= datosSerie.actores();
            this.sinopsis = datosSerie.sinopsis();
            //this.sinopsis = ConsultaCHATGPTAPI.obtenerTraduccion(datosSerie.sinopsis()); no tengo premium de GPT....
            this.poster= datosSerie.poster();
            this.genero = Categoria.fromString(datosSerie.genero().split(",")[0].trim());

    }

    @Override
    public String toString() {
        return  "titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", evaluacion=" + evaluacion +
                ", poster='" + poster + '\'' +
                ", ->genero=" + genero +
                ", actores='" + actores + '\'' +
                ", sinopsis='" + sinopsis + '\''+
                ", Episodios ='" + episodios  + '\'';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }
}

