package utnfc.isi.back.sim.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PLATAFORMAS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Plataforma {
    @Id
    @SequenceGenerator(name="seq_plat", sequenceName="SEQ_PLATAFORMAS", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_plat")
    @Column(name = "PLAT_ID")
    private Integer id;

    @Column(name = "NOMBRE", nullable = false, length = 255)
    private String nombre;
}

