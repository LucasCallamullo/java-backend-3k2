package utnfc.isi.back.sim.repository;


import jakarta.persistence.TypedQuery;
import utnfc.isi.back.sim.domain.Genero;


import java.util.List;
import java.util.Optional;

public class JpaGeneroRepository
        extends JpaRepositoryBase<Genero, Integer>
        implements GeneroRepository {

    public JpaGeneroRepository() { super(Genero.class); }

    @Override
    public Optional<Genero> findByNombre(String nombre) {
        var em = em();
        try {
            TypedQuery<Genero> q = em.createQuery(
                "select g from Genero g where g.nombre = :n", Genero.class);
            q.setParameter("n", nombre);
            var list = q.getResultList();
            return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
        } finally { em.close(); }
    }

    @Override
    public List<Genero> findAllOrderByNombre() {
        var em = em();
        try {
            return em.createQuery("select g from Genero g order by g.nombre", Genero.class)
                     .getResultList();
        } finally { em.close(); }
    }
}

