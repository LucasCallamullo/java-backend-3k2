package spring_food.demo.services.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    protected BaseService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public List<T> getAllList() {
        return repository.findAll();
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public List<T> saveAll(Iterable<T> entities) {
        /*
         * 
         * Django bulk_create: hace un único INSERT masivo en la DB → mucho más eficiente.
         * JPA saveAll: por defecto hace un INSERT por cada entidad (no es un “bulk” real a nivel SQL).
         *     Internamente llama a save(...) en loop.
         *     Pero Hibernate optimiza con batching si lo configurás (hibernate.jdbc.batch_size).
         * 
         */
        return repository.saveAll(entities);
    }
}

