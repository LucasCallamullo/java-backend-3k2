package pre.enunciado.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor        // OBLIGATORIO TE LO PIDE JPA
@AllArgsConstructor
@Builder
@Entity
@Table(name = "DESARROLLADORES")
public class Desarrollador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DESA_ID")
    private Integer desaId;

    @Column(name = "NOMBRE", nullable = true)
    private String nombre;

    // Getters y Setters
}