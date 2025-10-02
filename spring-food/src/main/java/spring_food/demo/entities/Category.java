package spring_food.demo.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Relación 1:N -> una Category puede tener muchos Foods
    // 'mappedBy = "category"' significa que la FK está en la entidad Food (campo "category")
    // cascade = CascadeType.PERSIST: si persistís la Category, también persiste automáticamente sus Foods
    // fetch = FetchType.LAZY: no carga los Foods automáticamente al leer una Category
    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    private Set<Food> foods;

    // Constructor de conveniencia para crear en memoria objetos Category sin id
    public Category(String name) {
        this.name = name;
    }
}
