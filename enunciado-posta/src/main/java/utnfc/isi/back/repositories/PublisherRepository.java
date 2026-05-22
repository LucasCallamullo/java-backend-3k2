package utnfc.isi.back.repositories;

import utnfc.isi.back.entities.Publisher;

public class PublisherRepository extends Repository<Publisher, Integer> {

    public PublisherRepository() {
        super();
    }

    @Override
    protected Class<Publisher> getEntityClass(){
        return Publisher.class;
    }
}