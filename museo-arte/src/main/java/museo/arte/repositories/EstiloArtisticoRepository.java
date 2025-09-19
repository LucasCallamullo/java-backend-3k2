package museo.arte.repositories;

import museo.arte.entities.EstiloArtistico;

/**
 * Repositorio específico para la entidad EstiloArtistico.
 * 
 * Extiende de Repository<T,K> para heredar métodos CRUD genéricos:
 *   - add(T entity)
 *   - update(T entity)
 *   - delete(K id)
 *
 * Aquí se agregan consultas y operaciones propias de la entidad EstiloArtistico.
 */
public class EstiloArtisticoRepository extends Repository<EstiloArtistico, Integer> {

    /**
     * Constructor que llama al constructor del Repository genérico
     * para inicializar el EntityManager.
     */
    public EstiloArtisticoRepository() {
        super();
    }

    @Override
    protected Class<EstiloArtistico> getEntityClass() {
        return EstiloArtistico.class;
    }
}

