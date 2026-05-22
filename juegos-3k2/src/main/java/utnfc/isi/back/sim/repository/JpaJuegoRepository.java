package utnfc.isi.back.sim.repository;

import jakarta.persistence.TypedQuery;
import utnfc.isi.back.sim.domain.*;
import utnfc.isi.back.sim.infra.LocalEntityManagerProvider;

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

    @Override
    public List<Object[]> top5GenerosPorJugando() {
        var em = LocalEntityManagerProvider.em();
        try {
            var q = em.createQuery("""
        select g.nombre, coalesce(sum(j.jugando),0)
        from Juego j join j.genero g
        group by g.nombre
        order by coalesce(sum(j.jugando),0) desc
        """, Object[].class);
            q.setMaxResults(5);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Object[]> desarrolladoresConMasDeN(int n) {
        var em = LocalEntityManagerProvider.em();
        try {
            var q = em.createQuery("""
        select d.nombre, count(j)
        from Juego j join j.desarrollador d
        group by d.nombre
        having count(j) > :n
        order by count(j) desc, d.nombre asc
        """, Object[].class);
            q.setParameter("n", (long) n);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Object[]> rankingPlataformasPorRatingPromedio(int minFinalizados) {
        var em = LocalEntityManagerProvider.em();
        try {
            var q = em.createQuery("""
        select p.nombre, avg(j.rating)
        from Juego j join j.plataforma p
        where j.rating is not null and j.juegosFinalizados is not null and j.juegosFinalizados > :min
        group by p.nombre
        order by avg(j.rating) desc
        """, Object[].class);
            q.setParameter("min", minFinalizados);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
