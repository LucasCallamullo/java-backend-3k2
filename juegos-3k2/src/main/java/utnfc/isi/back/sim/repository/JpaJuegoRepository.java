package utnfc.isi.back.sim.repository;

import jakarta.persistence.TypedQuery;
import utnfc.isi.back.sim.domain.*;

import java.util.List;
import java.util.Optional;

public class JpaJuegoRepository
        extends JpaRepositoryBase<Juego, Integer>
        implements JuegoRepository {

    public JpaJuegoRepository() {
        super(Juego.class);
    }

    @Override
    public Optional<Juego> findByTitulo(String titulo) {
        var em = em();
        try {
            TypedQuery<Juego> q = em.createQuery(
                    "select j from Juego j where j.titulo = :t", Juego.class);
            q.setParameter("t", titulo);
            var list = q.getResultList();
            return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Juego> findByGenero(Genero genero) {
        var em = em();
        try {
            return em.createQuery(
                    "select j from Juego j where j.genero = :g", Juego.class)
                    .setParameter("g", genero)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Juego> findByDesarrollador(Desarrollador dev) {
        var em = em();
        try {
            return em.createQuery(
                    "select j from Juego j where j.desarrollador = :d", Juego.class)
                    .setParameter("d", dev)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Juego> findByPlataforma(Plataforma plat) {
        var em = em();
        try {
            return em.createQuery(
                    "select j from Juego j where j.plataforma = :p", Juego.class)
                    .setParameter("p", plat)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Juego> findAllWithRefs() {
        var em = em();
        try {
            return em.createQuery(
                    "select distinct j "
                    + "from Juego j "
                    + "left join fetch j.desarrollador "
                    + "left join fetch j.plataforma "
                    + "left join fetch j.genero "
                    + "order by j.titulo", Juego.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
