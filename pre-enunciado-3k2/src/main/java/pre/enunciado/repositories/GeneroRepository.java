package pre.enunciado.repositories;

import pre.enunciado.entities.Genero;
import java.util.List;
import jakarta.persistence.TypedQuery; // Consulta tipada en JPA/Hibernate


/**
 * Repositorio específico para la entidad Autor.
 * 
 * Extiende de Repository<T,K> para heredar métodos CRUD genéricos:
 *   - add(T entity)
 *   - update(T entity)
 *   - delete(K id)
 *
 * Aquí se agregan consultas y operaciones propias de la entidad Autor.
 */
public class GeneroRepository extends Repository<Genero, Integer> {

    /**
     * Constructor que llama al constructor del Repository genérico
     * para inicializar el EntityManager.
     */
    public GeneroRepository() {
        super();
    }

    /**
     * Método genérico para recuperar una entidad por su campo 'nombre'.
     * - Funciona si la entidad T tiene un atributo llamado 'nombre'.
     * - Devuelve null si no se encuentra ninguna coincidencia.
     */
    public Genero getByName(String name) {
        // Construimos la query JPQL dinámicamente usando el nombre de la clase T
        String jpql = "SELECT e FROM " + getEntityClass().getSimpleName() + " e WHERE e.nombre = :name";
        
        TypedQuery<Genero> query = manager.createQuery(jpql, getEntityClass());
        query.setParameter("name", name); // asigna el parámetro ':name' al valor pasado
        List<Genero> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    protected Class<Genero> getEntityClass() {
        return Genero.class;
    }
}
