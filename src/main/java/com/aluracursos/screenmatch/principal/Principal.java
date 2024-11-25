package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;
import com.aluracursos.screenmatch.repositorio.SerieRepository;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=8eb50e5";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private SerieRepository repositorio;
    private List<Serie> series;


    public Principal(SerieRepository repository){
        this.repositorio= repository;

    }
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
        //DatosSerie datosSerie = getDatosSerie();
        mostrarSeriesBuscadas();
        System.out.println("Ingresar nombre de la Serie: ");
        var nombreserie=teclado.nextLine();

        Optional<Serie> serie = series.stream()
                .filter(f -> f.getTitulo().toLowerCase().contains(nombreserie.toLowerCase()))
                .findFirst();
        if(serie.isPresent()){

            var serieEncontrada= serie.get();

        List<DatosTemporadas> temporadas = new ArrayList<>();

        for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
            var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporada);
        }

        temporadas.forEach(System.out::println);
        List<Episodio> episodios = temporadas.stream()
                .flatMap(d -> d.episodios().stream()
                        .map(e ->new Episodio(d.numero(),e)))
                .collect(Collectors.toList());
        serieEncontrada.setEpisodios(episodios);
        repositorio.save(serieEncontrada);

        }
    }
    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie(); //variable que almacena datos de la API tratados en getDatosSerie
       Serie serie = new Serie(datos); //
       repositorio.save(serie);//utilizo la funcion save de la interfaz para guardar los datos en base de datos(por eso comente la linea de abajo)
        // datosSeries.add(datos); //guardo toda la lista de series en variable datos(que es lista tambien)...
        System.out.println(datos);


    }
    private void mostrarSeriesBuscadas() {

            series = repositorio.findAll(); //funcion de spring que trae todos los datos de la base de datos
        //en la documentacion esta toda la informacion de los metodos predeteminados --->


                    series.stream()
                        .sorted(Comparator.comparing(Serie::getGenero))
                        .forEach(System.out::println);



    }

}



