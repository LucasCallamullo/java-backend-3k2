package museo.arte.services;

import java.io.File;
import java.io.IOException;

import museo.arte.repositories.ObraArtisticaRepository;
import museo.arte.entities.ObraArtistica;


public class ObraArtisticaService {

    private final ObraArtisticaRepository obraArtisticaRepository;
    private final AutorService autorService;
    private final MuseoService museoService;
    private final EstiloArtisticoService estiloArtisticoService;

    public ObraArtisticaService() {
        obraArtisticaRepository = new ObraArtisticaRepository();
        autorService = new AutorService();
        museoService = new MuseoService();
        estiloArtisticoService = new EstiloArtisticoService();
    }

    public void bulkInsert(File fileToImport) throws IOException {
        Files.lines(Paths.get(fileToImport.toURI()))
                .skip(1)
                .forEach(linea -> {
                    ObraArtistica obra = procesarLinea(linea);
                    if (!this.obraArtisticaRepository.existsByName(obra.getNombre())) {
                        this.obraArtisticaRepository.add(obra);
                    }
                });
    }

    private ObraArtistica procesarLinea(String linea) {
        String[] tokens = linea.split(",");
        ObraArtistica obra = new ObraArtistica();

        String nombre = tokens[2];
        var autor = autorService.getOrCreateByName(nombre);
        obra.setAutor(autor);

        nombre = tokens[3];
        var museo = museoService.getOrCreateByName(nombre);
        obra.setMuseo(museo);

        nombre = tokens[4];
        var estilo = estiloArtisticoService.getOrCreateByName(nombre);
        obra.setEstiloArtistico(estilo);

        obra.setNombre(tokens[0]);
        obra.setAnio(tokens[1]);
        obra.setMontoAsegurado(Double.parseDouble(tokens[5]));
        obra.setSeguroTotal(tokens[6].equalsIgnoreCase("1"));

        return obra;
    }
}