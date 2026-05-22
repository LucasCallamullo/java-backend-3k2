package utnfc.isi.back.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CATEGORIES")
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_category")
    @SequenceGenerator(name = "seq_category", sequenceName = "SEQ_CATEGORY_ID", allocationSize = 1)
    @Column(name = "ID_CATEGORY")
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true, length = 120)
    private String name;
}
