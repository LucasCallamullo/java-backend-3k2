package pre.enunciado.repositories;

import java.util.List;

import jakarta.persistence.TypedQuery;
import pre.enunciado.entities.Juego;
// import java.util.List;
// import jakarta.persistence.TypedQuery; // Consulta tipada en JPA/Hibernate


public class JuegoRepository extends Repository<Juego, Integer> {

    /**
     * Constructor que llama al constructor del Repository genérico
     * para inicializar el EntityManager.
     */
    public JuegoRepository() {
        super();
    }

    /**
     * Método genérico para recuperar una entidad por su campo 'nombre'.
     * - Funciona si la entidad T tiene un atributo llamado 'nombre'.
     * - Devuelve null si no se encuentra ninguna coincidencia.
    /* o podriamos asociarlo a titulo... */
    @Override
    public Juego getByName(String name) {
        // Construimos la query JPQL dinámicamente usando el nombre de la clase T
        String jpql = "SELECT e FROM " + getEntityClass().getSimpleName() + " e WHERE e.titulo = :name";
        
        TypedQuery<Juego> query = manager.createQuery(jpql, getEntityClass());
        query.setParameter("name", name); // asigna el parámetro ':name' al valor pasado
        List<Juego> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    protected Class<Juego> getEntityClass() {
        return Juego.class;
    }
}
