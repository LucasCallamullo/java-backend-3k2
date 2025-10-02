package pre.enunciado.services;

import pre.enunciado.entities.Desarrollador;
import pre.enunciado.repositories.DesarrolladorRepository;

import pre.enunciado.services.interfaces.AbstractService;

public class DesarrolladorService extends AbstractService<Desarrollador, Integer> {

    public DesarrolladorService() {
        super(new DesarrolladorRepository());
    }

    @Override
    protected Desarrollador createNewEntity(String name) {
        Desarrollador genero = new Desarrollador();
        genero.setNombre(name);
        return genero;
    }
}