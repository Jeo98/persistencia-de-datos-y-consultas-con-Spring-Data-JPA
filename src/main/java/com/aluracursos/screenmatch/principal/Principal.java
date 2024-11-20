package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Serie;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=8eb50e5";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();

    public void muestraElMenu() {


        var opcion = -1;

            while (opcion != 0) {
                try {
                    var menu = """
                            1 - Buscar series 
                            2 - Buscar episodios
                            3 - Mostrar series buscadas
                            
                            0 - Salir
                            
                            ->
                            """;
                    System.out.println(menu);
                    opcion = teclado.nextInt();
                    teclado.nextLine();

                    switch (opcion) {
                        case 1:
                            buscarSerieWeb();
                            break;
                        case 2:
                            buscarEpisodioPorSerie();
                            break;
                        case 3:
                            mostrarSeriesBuscadas();
                            //datosSeries.forEach(System.out::println);
                            break;

                        case 0:
                            System.out.println("Cerrando la aplicación...");
                            break;
                        default:
                            System.out.println("Opción inválida");
                    }
                }catch (NumberFormatException e){
                    System.out.println("error...");
                }

            } //endwhile


    }



    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }
    private void buscarEpisodioPorSerie() {
        DatosSerie datosSerie = getDatosSerie();
        List<DatosTemporadas> temporadas = new ArrayList<>();

        for (int i = 1; i <= datosSerie.totalTemporadas(); i++) {
            var json = consumoApi.obtenerDatos(URL_BASE + datosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporada);
        }
        temporadas.forEach(System.out::println);
    }
    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie(); //variable que almacena datos de la API tratados en getDatosSerie
        datosSeries.add(datos); //guardo toda la lista de series en variable datos(que es lista tambien)...
        System.out.println(datos);


    }
    private void mostrarSeriesBuscadas() {

            List<Serie> series =new ArrayList<>();

            series = datosSeries.stream().map(s -> new Serie(s))
                        .collect(Collectors.toList());

            series.stream()
                        .sorted(Comparator.comparing(Serie::getGenero))
                        .forEach(System.out::println);



    }

}

