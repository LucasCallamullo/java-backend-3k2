package utnfc.isi.back.sim.repository;

import jakarta.persistence.TypedQuery;
import utnfc.isi.back.sim.domain.Desarrollador;

import java.util.List;
import java.util.Optional;

public class JpaDesarrolladorRepository
        extends JpaRepositoryBase<Desarrollador, Integer>
        implements DesarrolladorRepository {

    public JpaDesarrolladorRepository() {
        super(Desarrollador.class);
    }

    @Override
    public Optional<Desarrollador> findByNombre(String nombre) {
        var em = em();
        try {
            TypedQuery<Desarrollador> q = em.createQuery(
                "select d from Desarrollador d where d.nombre = :n", Desarrollador.class);
            q.setParameter("n", nombre);
            var list = q.getResultList();
            return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Desarrollador> findAll() {
        var em = em();
        try {
            return em.createQuery("select d from Desarrollador d order by d.nombre", Desarrollador.class)
                     .getResultList();
        } finally { em.close(); }
    }
}

