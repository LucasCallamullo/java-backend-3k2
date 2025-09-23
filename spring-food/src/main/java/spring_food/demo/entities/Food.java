package spring_food.demo.entities;


import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private double calories;
}


