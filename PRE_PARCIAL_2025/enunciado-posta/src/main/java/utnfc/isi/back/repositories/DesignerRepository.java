package utnfc.isi.back.repositories;

import utnfc.isi.back.entities.Designer;

public class DesignerRepository extends Repository<Designer, Integer> {

    public DesignerRepository() {
        super();
    }

    @Override
    protected Class<Designer> getEntityClass(){
        return Designer.class;
    }
}