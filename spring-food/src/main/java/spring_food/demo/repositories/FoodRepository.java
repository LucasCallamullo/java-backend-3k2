package spring_food.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_food.demo.entities.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
}



