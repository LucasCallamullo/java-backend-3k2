package utnfc.isi.back.sim.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DESARROLLADORES")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Desarrollador {
    @Id
    @SequenceGenerator(name="seq_desa", sequenceName="SEQ_DESARROLLADORES", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_desa")
    @Column(name = "DESA_ID")
    private Integer id;

    @Column(name = "NOMBRE", nullable = false, length = 255)
    private String nombre;
}

