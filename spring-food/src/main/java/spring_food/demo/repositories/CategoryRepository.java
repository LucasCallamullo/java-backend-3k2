package spring_food.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_food.demo.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
