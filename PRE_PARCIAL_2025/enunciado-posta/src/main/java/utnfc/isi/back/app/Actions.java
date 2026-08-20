package utnfc.isi.back.app;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
/* 
import java.nio.file.Path;
import java.io.BufferedWriter;

import java.util.Scanner;
import java.util.Map; */
import utnfc.isi.back.services.BoardGameService;


public class Actions {

    /* 
     * Método de ejemplo (del profesor) que permite importar empleados desde un archivo CSV.
     * Básicamente busca en un directorio archivos CSV que contengan la palabra "empleado" 
     * y los carga en el sistema usando el servicio EmpleadoService.
     */
    public void importarDesdeCsv(AppContext context) {
        // Obtiene del contexto (AppContext) la URL donde están los archivos a importar
        var pathToImport = (URL) context.get("path");

        // Bloque try-with-resources: recorre todos los archivos dentro del directorio indicado
        try (var paths = Files.walk(Paths.get(pathToImport.toURI()))) {
            
            // Se filtran los archivos encontrados:
            // 1. Solo se toman archivos regulares (no directorios)
            // 2. Que terminen en ".csv"
            // 3. Luego se convierten a objetos File y se guardan en una lista
            var csvFiles = paths
                    .filter(Files::isRegularFile)               // solo archivos, no carpetas
                    .filter(path -> path.toString().endsWith(".csv")) // que terminen en ".csv"
                    .map(path -> path.toFile())                 // convertir Path → File
                    .toList();                                  // recolectar en lista

            // Se procesa la lista de archivos CSV:
            // 1. Busca el primer archivo cuyo nombre contenga la palabra "empleado"
            // 2. Si lo encuentra → lo pasa al servicio para cargar empleados
            // 3. Si no lo encuentra → lanza una excepción
            csvFiles.stream()
                    .filter(f -> f.getName().contains("games"))  // buscar archivo con "empleado" en el nombre
                    .findFirst()                                   // quedarse con el primero
                    .ifPresentOrElse(f -> {                        // si existe:
                        // Obtener el servicio de empleados desde el contexto
                        var service = context.getService(BoardGameService.class);
                        try {
                            // Insertar en bloque todos los empleados del archivo CSV
                            service.bulkInsertCsv(f);
                        } catch (IOException e) {
                            e.printStackTrace(); // manejar error de lectura del archivo
                        }
                    },
                    () -> {
                        // Si no se encontró ningún archivo válido, lanzar excepción
                        throw new IllegalArgumentException("Archivo inexistente");
                    });

        } catch (IOException | URISyntaxException e) {
            // Manejo de errores: problemas de acceso al archivo o conversión de URI
            e.printStackTrace();
        }
    }

    public void listarJuegos(AppContext context) {
        // obtengo el servicio y la lista de juegos
        var service = context.getService(BoardGameService.class);
        var games = service.getAllList();

        if (games.isEmpty()) {
            System.out.println("No hay juegos registrados en la base de datos.");
        } else {
            System.out.println("Lista de juegos de mesa:");
            games.forEach(System.out::println);    // llamo al metodo to string de mi clase
        }
    }


    /*
     * Opcion 3
     * 
     * Mostrar las 5 categorías con mayor cantidad de jugadores registrados (usersRating) sumados por categoría.
        Indicaciones:
            - Orden descendente por total de usuarios (usersRating).
            - Si hay empate, ordenar alfabéticamente por nombre de categoría.
            - Ignorar juegos sin usersRating.
     */
    public void topFivePlayersForCategory(AppContext context){
        // obtengo el servicio y la lista de juegos
        var service = context.getService(BoardGameService.class);
        var games = service.getAllList();
        
        var mapa = service.calculeTopFivePlayersForCategory(games);
        mapa.forEach((category, totalPlayers) -> {
            System.out.println(
                "Categoría: " + category + " - Total Jugadores: " + totalPlayers
            );
        });
    }


    /*
     * Opcion 4) Diseñadores con más de 30 juegos
        Listar los diseñadores que tienen más de 30 juegos registrados.
        Indicaciones:
            Mostrar nombre del diseñador y cantidad de juegos.
     */
    public void getDesignersThirdteenMore(AppContext context){
        // obtengo el servicio y la lista de juegos
        var service = context.getService(BoardGameService.class);
        var games = service.getAllList();
        
        var map = service.getDesignersWithMoreThanThirtyGames(games);
        if (map.isEmpty()) {
            System.out.println("No hay diseñadores con más de 30 juegos registrados.");
        } else {
            System.out.println("Diseñadores con más de 30 juegos:");
            map.forEach((name, count) -> System.out.println("- " + name + " (" + count + " juegos)"));
        }
    }


    /*
     * Opcion 5) Mejor juego según rating promedio con filtros
     * 
     * Obtener el juego con mayor averageRating considerando solo juegos 
     * que tengan más de 999 usuarios (usersRating).
     * 
     * En caso de empate en averageRating, mostrar el juego más reciente (yearPublished).
     */
    public void bestGameByRating(AppContext context){
        // obtengo el servicio y la lista de juegos
        var service = context.getService(BoardGameService.class);
        var games = service.getAllList();

        var best = service.getBestGameByRating(games);
        if (best != null) {
            System.out.println("🏆 Mejor juego: " + best.getName() + " (" + best.getAverageRating() + ")");
        } else {
            System.out.println("No hay juegos con más de 999 usuarios.");
        }
    }


    public void listGamesByAgeAndPlayers (AppContext context){
        // obtengo el servicio y la lista de juegos
        var service = context.getService(BoardGameService.class);
        var games = service.getAllList();

        service.getListGamesByAgeAndPlayers(games);
    }
}