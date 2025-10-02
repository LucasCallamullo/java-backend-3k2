package pre.enunciado.services;

import pre.enunciado.entities.Plataforma;
import pre.enunciado.repositories.PlataformaRepository;

import pre.enunciado.services.interfaces.AbstractService;

public class PlataformaService extends AbstractService<Plataforma, Integer> {

    public PlataformaService() {
        super(new PlataformaRepository());
    }

    @Override
    protected Plataforma createNewEntity(String name) {
        Plataforma genero = new Plataforma();
        genero.setNombre(name);
        return genero;
    }
}