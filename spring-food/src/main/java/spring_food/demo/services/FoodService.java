package spring_food.demo.services;

import org.springframework.stereotype.Service;
import spring_food.demo.entities.Food;
import spring_food.demo.repositories.FoodRepository;
import spring_food.demo.services.interfaces.BaseService;

import java.util.List;

@Service
public class FoodService extends BaseService<Food, Long> {

    private final FoodRepository repository;

    public FoodService(FoodRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<Food> findByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public List<Food> findByCategory(String name) {
        return repository.findByCategoryName(name);
    }
}
