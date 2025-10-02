package pre.enunciado.entities;

import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor        // Requerido por JPA: necesita un constructor vacío (al menos protected o public)
@AllArgsConstructor       // Constructor con todos los atributos
@Builder                  // Permite crear objetos con el patrón Builder
@Entity
@Table(name = "GENEROS")  // Asocia esta entidad con la tabla GENEROS en la BD
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental (depende de la BD, en H2 usa sequence)
    @Column(name = "GEN_ID")
    private Integer genId;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    /*
     * Relación inversa con Juego: un género puede estar asociado a muchos juegos.
     *
     * @OneToMany -> Se indica la relación 1:N (un Género tiene muchos Juegos).
     * mappedBy = "genero" -> Significa que la FK está en la entidad Juego, en el campo 'genero'.
     * cascade = CascadeType.PERSIST -> Cuando se guarda un Género nuevo, sus Juegos asociados también se guardan automáticamente.
     * fetch = FetchType.LAZY -> Los Juegos NO se cargan automáticamente al traer un Género, 
     *                           se traen solo cuando se invoca explícitamente el getter (mejora rendimiento).
     *
     * @ToString.Exclude -> Evita un ciclo infinito en el método toString() generado por Lombok
     *                      (porque Juego también tiene una referencia al Género).
     */
    @OneToMany(mappedBy = "genero", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Juego> juegos;
}
