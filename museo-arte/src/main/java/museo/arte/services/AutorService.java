package museo.arte.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import museo.arte.entities.Autor;
import museo.arte.repositories.AutorRepository;

public class AutorService implements IService<Autor, Integer> {

    private final AutorRepository autorRepository;

    public AutorService() {
        this.autorRepository = new AutorRepository();
    }

    /**
     * Recupera un autor por ID.
     * - Devuelve null si no existe.
     */
    @Override
    public Autor getById(Integer id) {
        return autorRepository.getById(id);
    }

    /**
     * Recupera un autor por nombre, y si no existe lo crea.
     */
    @Override
    public Autor getOrCreateByName(String nombre) {
        Autor autor = autorRepository.getByName(nombre);
        if (autor == null) {
            autor = new Autor();
            autor.setNombre(nombre);
            autorRepository.create(autor);
        }
        return autor;
    }

    /**
     * Verifica si existe un autor por ID.
     */
    @Override
    public boolean existsById(Integer id) {
        return autorRepository.getById(id) != null;
    }

    /**
     * Verifica si existe un autor por nombre.
     */
    @Override
    public boolean existsByName(String nombre) {
        return autorRepository.existsByName(nombre);
    }

    /**
     * Devuelve todos los autores como lista.
     */
    @Override
    public List<Autor> getAll() {
        return autorRepository.getAllList();  // usa el genérico
    }

    /**
     * Devuelve todos los autores como Stream.
     */
    public Stream<Autor> getAllStream() {
        return autorRepository.getAllStream();  // ya existe genérico
    }
}
