package pre.enunciado.services;

import pre.enunciado.entities.Genero;
import pre.enunciado.repositories.GeneroRepository;

import pre.enunciado.services.interfaces.AbstractService;

public class GeneroService extends AbstractService<Genero, Integer> {

    public GeneroService() {
        super(new GeneroRepository());
    }

    @Override
    protected Genero createNewEntity(String name) {
        Genero genero = new Genero();
        genero.setNombre(name);
        return genero;
    }
}

/*
public class GeneroService implements IService<Genero, Integer> {

    private final GeneroRepository repository;

    public GeneroService() {
        this.repository = new GeneroRepository();
    }

    /**
     * Recupera un autor por ID.
     * - Devuelve null si no existe.
     *
    @Override
    public Genero getById(Integer id) {
        return this.repository.getById(id);
    }

    /**
     * Recupera un autor por nombre, y si no existe lo crea.
     *
    @Override
    public Genero getOrCreateByName(String nombre) {
        Genero autor = this.repository.getByName(nombre);
        if (autor == null) {
            autor = new Genero();
            autor.setNombre(nombre);
            this.repository.create(autor);
        }
        return autor;
    }

    /**
     * Verifica si existe un autor por ID.
     *
    @Override
    public boolean existsById(Integer id) {
        return this.repository.getById(id) != null;
    }

    /**
     * Devuelve todos los autores como lista.
     *
    @Override
    public List<Genero> getAll() {
        return this.repository.getAllList();  // usa el genérico
    }

    /**
     * Devuelve todos los autores como Stream.
     *
    public Stream<Genero> getAllStream() {
        return this.repository.getAllStream();  // ya existe genérico
    }
} */
