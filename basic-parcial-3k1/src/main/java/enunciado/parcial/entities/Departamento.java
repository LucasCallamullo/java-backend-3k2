package enunciado.parcial.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

// Anotaciones de Lombok para generar automáticamente código repetitivo:
// @Data -> genera getters, setters, toString, equals y hashCode
// @NoArgsConstructor -> genera un constructor vacío (requerido por JPA)
// @AllArgsConstructor -> genera un constructor con todos los atributos
// @Builder -> permite usar el patrón Builder para crear objetos fácilmente
// Define el nombre de la tabla en la base de datos: "departamento"

// Marca esta clase como una Entidad JPA, es decir, se mapeará a una tabla en la base de datos
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "departamento")
public class Departamento {

    // Marca este campo como la clave primaria de la tabla
    @Id
    // Estrategia IDENTITY -> en H2/MySQL/etc. el id se genera automáticamente (auto_increment)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Columna "nombre" de la tabla:
    // - no puede ser nula (nullable = false)
    // - máximo 100 caracteres (length = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    // Relación Uno a Muchos con Empleado:
    // Un departamento puede tener muchos empleados.
    @OneToMany(
        mappedBy = "departamento", // "departamento" es el atributo en la clase Empleado que referencia a Departamento
        cascade = CascadeType.ALL, // Propaga operaciones (persist, merge, remove, etc.) a los empleados
        // orphanRemoval = true,      // Si se elimina un empleado de la lista, también se elimina de la base de datos
        fetch = FetchType.LAZY     // Los empleados NO se cargan automáticamente, solo cuando se accede al atributo
    )
    private List<Empleado> empleados;
}

