package utnfc.isi.back.services;

import utnfc.isi.back.entities.Designer;
import utnfc.isi.back.repositories.DesignerRepository;

public class DesignerService extends AbstractService<Designer, Integer> {

    public DesignerService() {
        super(new DesignerRepository());
    }

    @Override
    protected Designer createNewEntity(String name) {
        Designer entity = new Designer();
        entity.setName(name);
        return entity;
    }
}