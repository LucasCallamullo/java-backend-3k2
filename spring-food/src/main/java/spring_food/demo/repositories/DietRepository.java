package spring_food.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_food.demo.entities.Diet;

public interface DietRepository extends JpaRepository<Diet, Long> {
}
