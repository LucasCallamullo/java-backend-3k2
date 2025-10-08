package enunciado.parcial.repositories;

import java.util.List;

/* import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream; */
import jakarta.persistence.TypedQuery; // Consulta tipada en JPA/Hibernate

import enunciado.parcial.entities.Empleado;

public class EmpleadoRepository extends Repository<Empleado, Integer> {

    public EmpleadoRepository() {
        super();
    }

    @Override
    protected Class<Empleado> getEntityClass(){
        return Empleado.class;
    }

    public List<Empleado> getEmpleadosFilterByAge(int edad) {
        // Construimos la query JPQL dinámicamente usando el nombre de la clase T
        String jpql = "SELECT e FROM " + getEntityClass().getSimpleName() + " e WHERE e.edad = :edad";
        
        TypedQuery<Empleado> query = manager.createQuery(jpql, getEntityClass());
        query.setParameter("edad", edad); // asigna el parámetro ':name' al valor pasado
        List<Empleado> results = query.getResultList();
        return results.isEmpty() ? null : results;
    }

    /* 
    @Override
    public Empleado getById(Integer id) {
        // Observen esto se hace directamente sin usar queies
        return this.manager.find(Empleado.class, id);
    }

    @Override
    public Set<Empleado> getAllSet() {
        return this.manager.createQuery("SELECT d FROM Empleado d", Empleado.class)
                .getResultList()
                .stream().collect(Collectors.toSet());
    }

    @Override
    public Stream<Empleado> getAllStrem() {
        return this.manager.createQuery("SELECT d FROM Empleado d", Empleado.class).getResultStream();
    }
    */
}
