package pre.enunciado.repositories;


import pre.enunciado.entities.Desarrollador;
import java.util.List;
import jakarta.persistence.TypedQuery;     // Consulta tipada en JPA/Hibernate


public class DesarrolladorRepository extends Repository<Desarrollador, Integer> {

    /**
     * Constructor que llama al constructor del Repository genérico
     * para inicializar el EntityManager.
     */
    public DesarrolladorRepository() {
        super();
    }

    public Desarrollador getByName(String name) {
        // Construimos la query JPQL dinámicamente usando el nombre de la clase T
        String jpql = "SELECT e FROM " + getEntityClass().getSimpleName() + " e WHERE e.nombre = :name";
        
        TypedQuery<Desarrollador> query = manager.createQuery(jpql, getEntityClass());
        query.setParameter("name", name);     // asigna el parámetro ':name' al valor pasado
        List<Desarrollador> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    protected Class<Desarrollador> getEntityClass() {
        return Desarrollador.class;
    }
}
