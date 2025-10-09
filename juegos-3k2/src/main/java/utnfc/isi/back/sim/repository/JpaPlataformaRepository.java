package utnfc.isi.back.sim.repository;

import jakarta.persistence.TypedQuery;
import utnfc.isi.back.sim.domain.Plataforma;

import java.util.List;
import java.util.Optional;

public class JpaPlataformaRepository
        extends JpaRepositoryBase<Plataforma, Integer>
        implements PlataformaRepository {

    public JpaPlataformaRepository() { super(Plataforma.class); }

    @Override
    public Optional<Plataforma> findByNombre(String nombre) {
        var em = em();
        try {
            TypedQuery<Plataforma> q = em.createQuery(
                "select p from Plataforma p where p.nombre = :n", Plataforma.class);
            q.setParameter("n", nombre);
            var list = q.getResultList();
            return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
        } finally { em.close(); }
    }

    @Override
    public List<Plataforma> findAllOrderByNombre() {
        var em = em();
        try {
            return em.createQuery("select p from Plataforma p order by p.nombre", Plataforma.class)
                     .getResultList();
        } finally { em.close(); }
    }
}

