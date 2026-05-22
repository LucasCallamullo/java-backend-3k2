package utnfc.isi.back.services;

import utnfc.isi.back.entities.Category;
import utnfc.isi.back.repositories.CategoryRepository;

public class CategoryService extends AbstractService<Category, Integer> {

    public CategoryService() {
        super(new CategoryRepository());
    }

    @Override
    protected Category createNewEntity(String name) {
        Category entity = new Category();
        entity.setName(name);
        return entity;
    }
}