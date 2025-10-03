package enunciado.parcial.repositories;

/* import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream; */

import enunciado.parcial.entities.Empleado;

public class EmpleadoRepository extends Repository<Empleado, Integer> {

    public EmpleadoRepository() {
        super();
    }

    @Override
    protected Class<Empleado> getEntityClass(){
        return Empleado.class;
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
