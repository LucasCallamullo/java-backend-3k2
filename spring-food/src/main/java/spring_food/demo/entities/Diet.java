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
public class Diet {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "diet", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    private Set<Food> foods;

    // este metodo es adicional para crear en memoria objetos de este tipo.
    public Diet(String name) {
        this.name = name;
    }
}
