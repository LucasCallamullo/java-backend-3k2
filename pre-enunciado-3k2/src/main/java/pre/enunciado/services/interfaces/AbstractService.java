package pre.enunciado.services.interfaces;

import java.util.List;
import pre.enunciado.repositories.Repository;

public abstract class AbstractService<T, K> implements IService<T, K> {

    protected final Repository<T, K> repository;

    // El constructor recibe el repository específico de cada entidad
    protected AbstractService(Repository<T, K> repository) {
        this.repository = repository;
    }

    @Override
    public T getById(K id) {
        return repository.getById(id);
    }

    @Override
    public boolean existsById(K id) {
        return repository.getById(id) != null;
    }

    @Override
    public List<T> getAllList() {
        return repository.getAllList();
    }

    @Override
    public T getOrCreateByName(String name) {

        // Esto asume que el repository tiene getByName
        T entity = repository.getByName(name);
        if (entity == null) {
            entity = createNewEntity(name);
            repository.create(entity);
        }
        return entity;
    }

    /**
     * Cada Service concreto define cómo crear una entidad nueva
     * a partir de un nombre (porque no todas tienen 'nombre').
     */
    protected abstract T createNewEntity(String name);
}
