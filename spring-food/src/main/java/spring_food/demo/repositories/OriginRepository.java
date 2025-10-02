package spring_food.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_food.demo.entities.Origin;

public interface OriginRepository extends JpaRepository<Origin, Long> {
}
