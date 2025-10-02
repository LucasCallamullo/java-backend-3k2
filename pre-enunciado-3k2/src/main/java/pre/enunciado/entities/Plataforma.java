package pre.enunciado.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "PLATAFORMAS")
@Data
@NoArgsConstructor        // OBLIGATORIO TE LO PIDE JPA
@AllArgsConstructor
@Builder
public class Plataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAT_ID")
    private Integer platId;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

}
