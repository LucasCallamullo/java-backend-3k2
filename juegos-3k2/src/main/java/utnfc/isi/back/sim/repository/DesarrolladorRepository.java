package utnfc.isi.back.sim.repository;

import utnfc.isi.back.sim.domain.Desarrollador;
import java.util.Optional;

public interface DesarrolladorRepository extends CrudRepository<Desarrollador, Integer> {
    Optional<Desarrollador> findByNombre(String nombre);
}

