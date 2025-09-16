package museo.arte.services;

import museo.arte.entities.Museo;
import museo.arte.repositories.MuseoRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Servicio para la entidad Museo.
 *
 * Implementa las operaciones de negocio y usa MuseoRepository
 * para acceder a la base de datos.
 */
public class MuseoService implements IService<Museo, Integer> {

    private final MuseoRepository museoRepository;

    public MuseoService() {
        this.museoRepository = new MuseoRepository();
    }

    /**
     * Recupera un museo por ID.
     * - Devuelve null si no existe.
     */
    @Override
    public Museo getById(Integer id) {
        return museoRepository.getById(id);
    }

    /**
     * Recupera un museo por nombre, y si no existe lo crea.
     */
    @Override
    public Museo getOrCreateByName(String nombre) {
        Museo museo = museoRepository.getByName(nombre);
        if (museo == null) {
            museo = new Museo();
            museo.setNombre(nombre);
            museoRepository.create(museo);
        }
        return museo;
    }

    /**
     * Verifica si existe un museo por ID.
     */
    @Override
    public boolean existsById(Integer id) {
        return museoRepository.getById(id) != null;
    }

    /**
     * Devuelve todos los museos como lista.
     */
    @Override
    public List<Museo> getAll() {
        return museoRepository.getAllList();
    }

    /* 🔹 Métodos adicionales opcionales:
       Si en algún momento querés exponer variantes como Stream o Set,
       podés agregarlas acá sin tocar la interfaz IService. */

    public Stream<Museo> getAllStream() {
        return museoRepository.getAllStream();
    }

    public List<String> getAllNames() {
        return museoRepository.getAllList()
                .stream()
                .map(Museo::getNombre)
                .collect(Collectors.toList());
    }
}
