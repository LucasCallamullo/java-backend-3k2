package utnfc.isi.back.services;

import utnfc.isi.back.entities.Publisher;
import utnfc.isi.back.repositories.PublisherRepository;

public class PublisherService extends AbstractService<Publisher, Integer> {

    public PublisherService() {
        super(new PublisherRepository());
    }

    @Override
    protected Publisher createNewEntity(String name) {
        Publisher entity = new Publisher();
        entity.setName(name);
        return entity;
    }
}