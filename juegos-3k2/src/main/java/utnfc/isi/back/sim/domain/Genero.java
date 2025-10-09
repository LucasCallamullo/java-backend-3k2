package utnfc.isi.back.sim.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GENEROS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Genero {
    @Id
    @SequenceGenerator(name="seq_gen", sequenceName="SEQ_GENEROS", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @Column(name = "GEN_ID")
    private Integer id;

    @Column(name = "NOMBRE", nullable = false, length = 255)
    private String nombre;
}

