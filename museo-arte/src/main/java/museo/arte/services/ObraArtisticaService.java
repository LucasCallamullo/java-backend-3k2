package museo.arte.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import museo.arte.entities.ObraArtistica;
import museo.arte.repositories.ObraArtisticaRepository;

public class ObraArtisticaService implements IService<ObraArtistica, Integer> {

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

    @Override
    public ObraArtistica getById(Integer id) {
        return obraArtisticaRepository.getById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return obraArtisticaRepository.getById(id) != null;
    }

    /**
     * Verifica si existe un autor por nombre.
     */
    @Override
    public boolean existsByName(String nombre) {
        return obraArtisticaRepository.existsByName(nombre);
    }

    @Override
    public ObraArtistica getOrCreateByName(String name) {
        if (!obraArtisticaRepository.existsByName(name)) {
            ObraArtistica obra = new ObraArtistica();
            obra.setNombre(name);
            obraArtisticaRepository.create(obra);
            return obra;
        }
        return obraArtisticaRepository.getByName(name);
    }

    @Override
    public List<ObraArtistica> getAll() {
        return obraArtisticaRepository.getAllList();
    }


    public void bulkInsert(File fileToImport) throws IOException {
        Files.lines(Paths.get(fileToImport.toURI()))
                .skip(1) // saltear cabecera
                .forEach(linea -> {
                    ObraArtistica obra = procesarLinea(linea);
                    // if (!this.obraArtisticaRepository.existsByName(obra.getNombre())) {
                    this.obraArtisticaRepository.create(obra);
                    // }
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
        obra.setEstilo(estilo);

        obra.setNombre(tokens[0]);
        obra.setAnio(tokens[1]);
        obra.setMontoAsegurado(Double.parseDouble(tokens[5]));
        obra.setSeguroTotal(tokens[6].equalsIgnoreCase("1"));

        return obra;
    }
}
