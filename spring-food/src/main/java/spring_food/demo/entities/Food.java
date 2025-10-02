package spring_food.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private double proteins;
    private double carbohydrates;
    private double fats;
    private double calories;

    /**
     * Relación muchos-a-uno: muchas Foods pueden pertenecer a una misma Category.
     *
     * cascade = CascadeType.PERSIST → cuando persistas un Food nuevo,
     * si la Category asociada todavía no existe en la DB, también se va a insertar.
     *
     * fetch = FetchType.EAGER → al cargar un Food desde la DB,
     * trae automáticamente la Category asociada en la misma consulta.
     * ⚠️ Esto puede aumentar memoria usada si hay muchas relaciones.
     * Para optimizar suele usarse LAZY, pero depende del caso de uso.
     */
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(
        name = "category_id",           // Columna FK en la tabla foods
        nullable = false,
        referencedColumnName = "id"     // PK en la tabla categories
    )
    @JsonManagedReference
    @ToString.Exclude
    private Category category;

    /**
     * Relación muchos-a-uno: muchas Foods pueden compartir el mismo Origin.
     *
     * fetch = LAZY → cuando traés un Food, no carga el Origin de inmediato.
     * Solo lo consulta si invocás food.getOrigin().
     * Esto evita problemas de rendimiento (N+1 queries) si no necesitás el Origin siempre.
     */
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "origin_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    @ToString.Exclude
    private Origin origin;

    /**
     * Relación muchos-a-uno: muchas Foods pueden compartir la misma Diet.
     *
     * cascade = PERSIST → si guardás un Food con una Diet nueva, también la persiste.
     * fetch = LAZY → no carga la Diet automáticamente hasta que se acceda al getter.
     */
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "diet_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    @ToString.Exclude
    private Diet diet;
}


