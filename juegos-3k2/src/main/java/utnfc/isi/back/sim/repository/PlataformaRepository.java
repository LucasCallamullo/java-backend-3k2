package utnfc.isi.back.sim.repository;

import java.util.Optional;

import utnfc.isi.back.sim.domain.Plataforma;

public interface PlataformaRepository extends CrudRepository<Plataforma, Integer> {

  public Optional<Plataforma> findByNombre(String nombre);

  public java.util.List<Plataforma> findAllOrderByNombre();


}

