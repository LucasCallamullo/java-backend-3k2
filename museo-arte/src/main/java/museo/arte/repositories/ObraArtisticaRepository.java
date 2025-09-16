package museo.arte.repositories;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import museo.arte.entities.ObraArtistica;

import jakarta.persistence.TypedQuery;

/**
 * Repositorio específico para la entidad ObraArtistica.
 * 
 * Extiende de Repository<T,K> para heredar métodos CRUD genéricos:
 *   - add(T entity)
 *   - update(T entity)
 *   - delete(K id)
 *
 * Aquí se agregan consultas y operaciones propias de la entidad ObraArtistica.
 */
public class ObraArtisticaRepository extends Repository<ObraArtistica, Integer> {

    /**
     * Constructor que llama al constructor del Repository genérico
     * para inicializar el EntityManager.
     */
    public ObraArtisticaRepository() {
        super();
    }

    @Override
    protected Class<ObraArtistica> getEntityClass() {
        return ObraArtistica.class;
    }

    /**
     * Ejemplo de método personalizado:
     * Recupera todas las ObrasArtísticas de un Museo específico.
     *
     * @param museoId ID del Museo
     * @return Set de ObrasArtísticas pertenecientes al museo
     */
    public Set<ObraArtistica> getByMuseo(int museoId) {
        TypedQuery<ObraArtistica> query = manager.createQuery(
            "SELECT o FROM ObraArtistica o WHERE o.museo.id = :museoId", ObraArtistica.class);
        query.setParameter("museoId", museoId);
        return query.getResultList().stream().collect(Collectors.toSet());
    }
}
