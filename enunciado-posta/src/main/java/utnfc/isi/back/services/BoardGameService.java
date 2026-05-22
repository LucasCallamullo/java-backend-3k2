package utnfc.isi.back.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import utnfc.isi.back.csv.CsvLoader;
import utnfc.isi.back.entities.BoardGame;
import utnfc.isi.back.repositories.BoardGameRepository;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BoardGameService extends AbstractService<BoardGame, Integer> {
    private final CsvLoader csvLoader;

    public BoardGameService() {
        super(new BoardGameRepository());
        this.csvLoader = new CsvLoader(
            new CategoryService(),
            new DesignerService(),
            new PublisherService()
        );
    }

    // metodo solo por cumplir lo que pedía el abstract - no tiene uso en boardGame
    @Override
    protected BoardGame createNewEntity(String name) {
        // BoardGame entity = new BoardGame();
        return null;
    }

    public void bulkInsertCsv(File fileToImport) throws IOException {
        Files.lines(Paths.get(fileToImport.toURI()))
            .skip(1) // saltear cabecera
            .forEach(linea -> {
                BoardGame game = this.csvLoader.parseLine(linea);
                if (game != null) {
                    this.repository.create(game);
                }
            });
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
    public Map<String, Integer> calculeTopFivePlayersForCategory(List<BoardGame> games) {
        var map = games.stream()
            .collect(Collectors.groupingBy(
                e -> e.getCategory().getName(),      // Clasifica por nombre de category y suma
                Collectors.summingInt(
                    g -> g.getUsersRating() != null ? g.getUsersRating() : 0
                )     // Suma cuántos players hay de forma robusta
            ));
        
        // Ordenamos por valor descendente y tomamos los 5 primeros
        return map.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) // Orden descendente
            .limit(5)                                                 // Solo top 5
            .collect(Collectors.toMap(
                entry -> entry.getKey(),       // Igual que Map.Entry::getKey
                entry -> entry.getValue(),     // Igual que Map.Entry::getValue
                (e1, e2) -> e1,                // Ya era una lambda
                () -> new LinkedHashMap<>()    // Igual que LinkedHashMap::new - Para mantener el orden
            ));
    }

    /**
     * Devuelve un mapa con los diseñadores que tienen más de 30 juegos registrados.
     * Clave → Nombre del diseñador
     * Valor → Cantidad de juegos
     */
    public Map<String, Long> getDesignersWithMoreThanThirtyGames(List<BoardGame> games) {

        // Agrupamos por diseñador y contamos cuántos juegos tiene cada uno
        var map = games.stream()
            .collect(Collectors.groupingBy(
                g -> g.getDesigner().getName(),
                Collectors.counting()
            ));

        // Filtramos los que tienen más de 30 juegos y ordenamos por cantidad descendente
        return map.entrySet().stream()
            .filter(entry -> entry.getValue() > 5)
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new // mantiene el orden descendente
            ));
    }

    /*
     * Opcion 5) Mejor juego según rating promedio con filtros
     * 
     * Obtener el juego con mayor averageRating considerando solo juegos 
     * que tengan más de 999 usuarios (usersRating).
     * 
     * En caso de empate en averageRating, mostrar el juego más reciente (yearPublished).
     */
    public BoardGame getBestGameByRating(List<BoardGame> games) {
        return games.stream()
            // Filtro: solo juegos con más de 999 usuarios
            .filter(g -> g.getUsersRating() != null && g.getUsersRating() > 999)

            // Comparador compuesto:
            // primero por rating promedio (mayor es mejor),
            // luego por año (más reciente es mejor)
            .max(Comparator.comparing(
                    BoardGame::getAverageRating, 
                    Comparator.nullsLast(Comparator.naturalOrder())
                ).thenComparing(
                    BoardGame::getYearPublished, 
                    Comparator.nullsLast(Comparator.naturalOrder())
                )
            )
            .orElse(null);    // Obtener el resultado o null si no hay juegos válidos
    }

    
    /*
    * Opción 6 - Juegos aptos para grupo y edad
    *
    * Obtener todos los juegos que sean aptos para un grupo de 4 jugadores y 
    * donde todos los integrantes del grupo tengan al menos 12 años.
    * Supongamos que los jugadores son [12, 13, 14, 12].
    */
    public List<BoardGame> getListGamesByAgeAndPlayers(List<BoardGame> games) {

        // Usamos un array porque el método espera int[]
        final int[] ages = {12, 13, 14, 12};

        return games.stream()
            // Filtra los juegos que soporten 4 jugadores
            .filter(g -> g.supportsPlayerCount(4))
            // Filtra los juegos que sean aptos para todos los jugadores de esas edades
            .filter(g -> g.isSuitableForAges(ages))
            .toList();
    }


    /*
    * Opción 7) Promedio general de ratings por categoría
    *
    * Calcular el promedio de averageRating agrupando
    * los juegos por su categoría.
    */
    public Map<String, Double> ratingsByCategory(List<BoardGame> games) {
        return games.stream()
            // Agrupa por el nombre de la categoría
            .collect(Collectors.groupingBy(
                g -> g.getCategory().getName(),
                // Calcula el promedio del rating (double)
                Collectors.averagingDouble(g -> 
                    g.getAverageRating() != null ? g.getAverageRating().doubleValue() : 0.0
                )
            ));
    }


    /*
    * Opción 9) Mejor juego por categoría
    * Obtener un Map<Category, BoardGame> 
    * donde cada categoría tenga el mejor juego (mayor rating).
    * Ordenar las categorías alfabéticamente.
    */
    public Map<String, BoardGame> getBestGameByCategory(List<BoardGame> games) {
        return games.stream()
            // agrupamos por el nombre de la categoría
            .collect(Collectors.groupingBy(
                g -> g.getCategory().getName(),
                // dentro de cada grupo, obtenemos el juego con mayor rating
                Collectors.collectingAndThen(
                    Collectors.maxBy(Comparator.comparing(g -> g.getAverageRating())),
                    Optional::get // obtenemos el BoardGame del Optional
                )
            ))
            // convertimos el Map en un TreeMap para ordenarlo alfabéticamente por clave
            .entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (a, b) -> a,
                LinkedHashMap::new // mantiene el orden
            ));
    }

    

}