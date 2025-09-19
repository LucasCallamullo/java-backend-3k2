package museo.arte.app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import museo.arte.services.ObraArtisticaService;
// import museo.arte.services.EstiloArtisticoService;

public class Actions {

    public void importarObras(AppContext context) {
        var pathToImport = (URL) context.get("path");

        try (var paths = Files.walk(Paths.get(pathToImport.toURI()))) {
            var csvFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".csv"))
                    .map(path -> path.toFile())
                    .toList();

            csvFiles.stream()
                    .filter(f -> f.getName().contains("obras"))
                    .findFirst()
                    .ifPresentOrElse(f -> {
                        var service = context.getService(ObraArtisticaService.class);
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

    public void listarObras(AppContext context) {
        var service = context.getService(ObraArtisticaService.class);

        // Recuperar todas las obras desde la BD
        var obras = service.getAll();

        if (obras.isEmpty()) {
            System.out.println("⚠ No hay obras registradas en la base de datos.");
        } else {
            System.out.println("📋 Lista de obras artísticas:");
            obras.forEach(obra -> {
                System.out.printf(
                        "ID: %d | Nombre: %s | Año: %s | Autor: %s | Museo: %s | Estilo: %s | Monto Asegurado: %.2f | Total: %s%n",
                        obra.getId(),
                        obra.getNombre(),
                        obra.getAnio(),
                        obra.getAutor() != null ? obra.getAutor().getNombre() : "Desconocido",
                        obra.getMuseo() != null ? obra.getMuseo().getNombre() : "Desconocido",
                        obra.getEstilo() != null ? obra.getEstilo().getNombre() : "Desconocido",
                        obra.getMontoAsegurado(),
                        obra.isSeguroTotal() ? "Sí" : "No"
                );
            });
        }
    }
}
