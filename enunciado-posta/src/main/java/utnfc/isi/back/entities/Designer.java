package utnfc.isi.back.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DESIGNERS")
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Designer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_designer")
    @SequenceGenerator(name = "seq_designer", sequenceName = "SEQ_DESIGNER_ID", allocationSize = 1)
    @Column(name = "ID_DESIGNER")
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true, length = 160)
    private String name;
}
