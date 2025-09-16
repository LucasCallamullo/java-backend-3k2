package museo.arte.repositories;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import museo.arte.entities.Museo;

import jakarta.persistence.TypedQuery;

/**
 * Repositorio específico para la entidad Museo.
 * 
 * Extiende de Repository<T,K> para heredar métodos CRUD genéricos:
 *   - add(T entity)
 *   - update(T entity)
 *   - delete(K id)
 *
 * Aquí se agregan consultas y operaciones propias de la entidad Museo.
 */
public class MuseoRepository extends Repository<Museo, Integer> {

    /**
     * Constructor que llama al constructor del Repository genérico
     * para inicializar el EntityManager.
     */
    public MuseoRepository() {
        super();
    }

    @Override
    protected Class<Museo> getEntityClass() {
        return Museo.class;
    }
}
