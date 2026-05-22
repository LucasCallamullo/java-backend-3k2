package utnfc.isi.back.repositories;

import utnfc.isi.back.entities.Category;

public class CategoryRepository extends Repository<Category, Integer> {

    public CategoryRepository() {
        super();
    }

    @Override
    protected Class<Category> getEntityClass(){
        return Category.class;
    }
}