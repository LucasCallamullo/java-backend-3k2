package utnfc.isi.back.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PUBLISHERS")
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_publisher")
    @SequenceGenerator(name = "seq_publisher", sequenceName = "SEQ_PUBLISHER_ID", allocationSize = 1)
    @Column(name = "ID_PUBLISHER")
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true, length = 160)
    private String name;
}
