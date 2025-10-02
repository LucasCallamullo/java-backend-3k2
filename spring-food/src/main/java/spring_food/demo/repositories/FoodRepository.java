package spring_food.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_food.demo.entities.Food;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByNameContainingIgnoreCase(String name);
    List<Food> findByCategoryName(String categoryName); // relación
}



