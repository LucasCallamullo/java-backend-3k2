package utnfc.isi.back.sim.repository;

import java.util.Optional;

import utnfc.isi.back.sim.domain.Juego;

public interface JuegoRepository extends CrudRepository<Juego, Integer> {

  public Optional<Juego> findByTitulo(String titulo);

  public java.util.List<Juego> findByGenero(utnfc.isi.back.sim.domain.Genero genero);

  public java.util.List<Juego> findByDesarrollador(utnfc.isi.back.sim.domain.Desarrollador dev);

  public java.util.List<Juego> findByPlataforma(utnfc.isi.back.sim.domain.Plataforma plat);

  public java.util.List<Juego> findAllWithRefs();

  public java.util.List<Object[]> top5GenerosPorJugando();

  public java.util.List<Object[]> desarrolladoresConMasDeN(int n);

  public java.util.List<Object[]> rankingPlataformasPorRatingPromedio(int minJuegos);


}

