package museo.arte.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import museo.arte.repositories.EstiloArtisticoRepository;
import museo.arte.entities.EstiloArtistico;

/**
 * Servicio para gestionar Estilos Artísticos.
 * Implementa las operaciones definidas en la interfaz IService.
 */
public class EstiloArtisticoService implements IService<EstiloArtistico, Integer> {

    private final EstiloArtisticoRepository estiloArtisticoRepository;

    public EstiloArtisticoService() {
        this.estiloArtisticoRepository = new EstiloArtisticoRepository();
    }

    /**
     * Recupera un estilo artístico por ID.
     * - Devuelve null si no existe.
     */
    @Override
    public EstiloArtistico getById(Integer id) {
        return estiloArtisticoRepository.getById(id);
    }

    /**
     * Recupera un estilo artístico por nombre, y si no existe lo crea.
     */
    @Override
    public EstiloArtistico getOrCreateByName(String nombre) {
        EstiloArtistico estilo = estiloArtisticoRepository.getByName(nombre);
        if (estilo == null) {
            estilo = new EstiloArtistico();
            estilo.setNombre(nombre);
            estiloArtisticoRepository.create(estilo);
        }
        return estilo;
    }

    /**
     * Verifica si existe un estilo artístico por ID.
     */
    @Override
    public boolean existsById(Integer id) {
        return estiloArtisticoRepository.getById(id) != null;
    }

    /**
     * Verifica si existe un estilo artístico por nombre.
     */
    @Override
    public boolean existsByName(String nombre) {
        return estiloArtisticoRepository.existsByName(nombre);
    }

    /**
     * Devuelve todos los estilos artísticos como lista.
     */
    @Override
    public List<EstiloArtistico> getAll() {
        return estiloArtisticoRepository.getAllList();
    }

    /**
     * Devuelve todos los estilos artísticos como Stream.
     */
    public Stream<EstiloArtistico> getAllStream() {
        return estiloArtisticoRepository.getAllStream();
    }
}