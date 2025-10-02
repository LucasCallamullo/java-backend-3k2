package spring_food.demo.services;

import org.springframework.stereotype.Service;

import spring_food.demo.entities.Category;
import spring_food.demo.repositories.CategoryRepository;
import spring_food.demo.services.interfaces.BaseService;


@Service
public class CategoryService extends BaseService<Category, Long> {
    public CategoryService(CategoryRepository repository) {
        super(repository);
    }
}