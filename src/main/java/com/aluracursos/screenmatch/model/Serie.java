package com.aluracursos.screenmatch.model;

import com.aluracursos.screenmatch.service.ConsultaCHATGPTAPI;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.OptionalDouble;

import static com.aluracursos.screenmatch.model.Categoria.*;

public class Serie {

    private String titulo;
    private Integer totalTemporadas;
    private Double evaluacion;
    private String poster;
    private Categoria genero;
    private String actores;
    private String sinopsis;

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
                ", sinopsis='" + sinopsis + '\'';
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
}
