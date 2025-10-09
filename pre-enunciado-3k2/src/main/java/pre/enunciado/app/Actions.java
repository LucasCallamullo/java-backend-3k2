package pre.enunciado.app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import pre.enunciado.services.JuegoService;


public class Actions {

    public void importarJuegos(AppContext context) {
        var pathToImport = (URL) context.get("path");

        try (var paths = Files.walk(Paths.get(pathToImport.toURI()))) {
            var csvFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".csv"))
                    .map(path -> path.toFile())
                    .toList();

            csvFiles.stream()
                    .filter(f -> f.getName().contains("games"))
                    .findFirst()
                    .ifPresentOrElse(f -> {
                        var service = context.getService(JuegoService.class);
                        try {
                            service.bulkInsert(f);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    },
                    () -> {
                        throw new IllegalArgumentException("Archivo inexistente");
                    });

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void listarJuegos(AppContext context) {
        var service = context.getService(JuegoService.class);

        // Recuperar todas las obras desde la BD
        var juegos = service.getAllList();

        if (juegos.isEmpty()) {
            System.out.println("⚠ No hay obras registradas en la base de datos.");
            return;
        }

        System.out.println("📋 Lista de obras artísticas:");
        juegos.forEach(j -> {
            System.out.printf(
                "ID: %d | Nombre: %s | Año: %d | ESRB: %s | Genero: %s %n",
                j.getJuegoId(),
                j.getTitulo(),
                j.getFechaLanzamiento(),
                j.getClasificacionEsrb(),
                j.getGenero() != null ? j.getGenero().getNombre() : "Desconocido"
            );
        });
    }
}
