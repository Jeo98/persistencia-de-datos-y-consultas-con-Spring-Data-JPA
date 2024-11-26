package com.aluracursos.screenmatch.model;

public enum Categoria {

    ACCION("Action" , "Accion"),

    ROMANCE("Romance" , "Romance"),

    COMEDIA("Comedy" , "Comedia"),

    DRAMA("Drama" , "Drama"),

    CRIMEN("Crime" , "Crimen");

  private String categoriaOmdb;
  private String categoriaEspaniol;

    Categoria (String categoriaOmdb, String categoriaEspaniol){

        this.categoriaOmdb= categoriaOmdb;
        this.categoriaEspaniol = categoriaEspaniol;
    }
    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }
    public static Categoria fromEspaniol(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaEspaniol.equalsIgnoreCase(text)) {
                return categoria;
            }
        }//verifica si el texto enviado por parametro hace match con alguno de los establecidos arriba
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }
}
