package enunciado.parcial.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Entity
@Table(name = "puesto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Puesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @OneToMany(
        mappedBy = "puesto", 
        // cascade = CascadeType.ALL, 
        // orphanRemoval = true
        fetch = FetchType.LAZY
    )
    private List<Empleado> empleados;
}
