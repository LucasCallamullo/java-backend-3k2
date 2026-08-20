package utnfc.isi.back.csv;

import utnfc.isi.back.services.CategoryService;
import utnfc.isi.back.services.DesignerService;
import utnfc.isi.back.services.PublisherService;
import utnfc.isi.back.entities.BoardGame;

public class CsvLoader {
    private static final String DELIMITER = ";";
    private static final boolean LOGS = false;
    private int lineCount = 1; // contador de líneas procesadas

    private final CategoryService categoryService;
    private final DesignerService designerService;
    private final PublisherService publisherService;

    public CsvLoader(CategoryService categoryService,
                 DesignerService designerService,
                 PublisherService publisherService) {
        this.categoryService = categoryService;
        this.designerService = designerService;
        this.publisherService = publisherService;
    }

    public BoardGame parseLine(String line) {
        String[] tokens = line.split(DELIMITER);
        lineCount++;

        if (tokens.length < 10) {
            if (LOGS) System.err.println("Línea " + lineCount + " incompleta: " + line);
            return null;
        }

        try {
            String name = tokens[0].trim();
            Integer year = parseInt(tokens[1]);
            Integer minAge = parseInt(tokens[2]);
            Double rating = parseDouble(tokens[3]);
            Integer users = parseInt(tokens[4]);
            Integer minPlayers = parseInt(tokens[5]);
            Integer maxPlayers = parseInt(tokens[6]);

            // Validaciones de datos
            if (year != null && (year < 1800 || year > 2100)) {
                if (LOGS) System.err.println("Línea " + lineCount + ": año fuera de rango (" + year + ")");
                return null;
            }
            if (minAge != null && minAge < 0) {
                if (LOGS) System.err.println("Línea " + lineCount + ": edad mínima inválida (" + minAge + ")");
                return null;
            }
            if (rating != null && (rating < 0 || rating > 10)) {
                if (LOGS) System.err.println("Línea " + lineCount + ": rating fuera de rango (" + rating + ")");
                return null;
            }
            // esta condicion es en caso de que mantengaan la ck de no se permite un unico nulo
            // O ambos están definidos, o ambos están nulos.
            // No se puede tener MIN_PLAYERS=NULL y MAX_PLAYERS=4.
            if ((minPlayers == null && maxPlayers != null) || (minPlayers != null && maxPlayers == null)) {
                if (LOGS) {
                    System.err.println(
                        "Línea " + lineCount + ": rango de jugadores incompleto (" 
                        + minPlayers + "/" + maxPlayers + ")"
                    );
                }
                return null;
            }
            if (minPlayers != null && minPlayers <= 0) {
                if (LOGS) System.err.println("Línea " + lineCount + ": minPlayers debe ser > 0 (" + minPlayers + ")");
                return null;
            }
            if (minPlayers != null && maxPlayers != null && maxPlayers < minPlayers) {
                if (LOGS) {
                    System.err.println(
                        "Línea " + lineCount + ": maxPlayers < minPlayers (" 
                        + maxPlayers + "/" + minPlayers + ")"
                    );
                }
                return null;
            }

            // relaciones fk
            String designerName = tokens[7].trim();
            String publisherName = tokens[8].trim();
            String categoryName = tokens[9].trim();

            if (designerName.isEmpty() || publisherName.isEmpty() || categoryName.isEmpty()) return null;

            return BoardGame.builder()
                    .name(name)
                    .yearPublished(year)
                    .minAge(minAge)
                    .averageRating(rating != null ? java.math.BigDecimal.valueOf(rating) : null)
                    .usersRating(users)
                    .minPlayers(minPlayers)
                    .maxPlayers(maxPlayers)
                    .designer(designerService.getOrCreateByName(designerName))
                    .publisher(publisherService.getOrCreateByName(publisherName))
                    .category(categoryService.getOrCreateByName(categoryName))
                    .build();

        } catch (NumberFormatException ex) {
            if (LOGS) System.err.println("Error de formato numérico en línea: " + lineCount + " incompleta: " + line);
            return null;
        }
    }

    private Integer parseInt(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty() || s.equalsIgnoreCase("null")) return null;
        return Integer.parseInt(s);
    }

    private Double parseDouble(String s) {
        if (s == null || s.trim().isEmpty() || s.equalsIgnoreCase("null")) return null;
        return Double.parseDouble(s.trim().replace(",", ".")); // acepta decimales con coma
    }
}
