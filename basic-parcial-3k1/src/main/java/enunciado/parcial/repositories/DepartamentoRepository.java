package enunciado.parcial.repositories;

/* import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream; */

import enunciado.parcial.entities.Departamento;

public class DepartamentoRepository extends Repository<Departamento, Integer> {

    public DepartamentoRepository() {
        super();
    }

    @Override
    protected Class<Departamento> getEntityClass(){
        return Departamento.class;
    }

    /* 
    @Override
    public Departamento getById(Integer id) {
        // Observen esto se hace directamente sin usar queies
        return this.manager.find(Departamento.class, id);
    }

    @Override
    public Set<Departamento> getAllSet() {
        return this.manager.createQuery("SELECT d FROM Departamento d", Departamento.class)
                .getResultList()
                .stream().collect(Collectors.toSet());
    }

    @Override
    public Stream<Departamento> getAllStrem() {
        return this.manager.createQuery("SELECT d FROM Departamento d", Departamento.class).getResultStream();
    }

    @Override
    public Departamento getByName(String name) {
        // Crear la query JPQL con parámetro
        TypedQuery<Departamento> query = manager.createQuery(
            "SELECT d FROM Departamento d WHERE d.nombre = :name", Departamento.class
        );

        // Reemplaza ":name" por el valor real pasado al método
        query.setParameter("name", name);

        // Ejecuta la query y obtiene la lista de resultados
        List<Departamento> results = query.getResultList();

        // Retorna null si no hay resultados, sino el primero
        return results.isEmpty() ? null : results.get(0);
    }
    */
}
