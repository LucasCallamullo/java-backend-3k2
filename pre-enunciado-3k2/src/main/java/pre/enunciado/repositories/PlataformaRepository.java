package pre.enunciado.repositories;

import pre.enunciado.entities.Plataforma;
import java.util.List;
import jakarta.persistence.TypedQuery;     // Consulta tipada en JPA/Hibernate


public class PlataformaRepository extends Repository<Plataforma, Integer> {

    /**
     * Constructor que llama al constructor del Repository genérico
     * para inicializar el EntityManager.
     */
    public PlataformaRepository() {
        super();
    }

    public Plataforma getByName(String name) {
        // Construimos la query JPQL dinámicamente usando el nombre de la clase T
        String jpql = "SELECT e FROM " + getEntityClass().getSimpleName() + " e WHERE e.nombre = :name";
        
        TypedQuery<Plataforma> query = manager.createQuery(jpql, getEntityClass());
        query.setParameter("name", name);     // asigna el parámetro ':name' al valor pasado
        List<Plataforma> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    protected Class<Plataforma> getEntityClass() {
        return Plataforma.class;
    }
}
