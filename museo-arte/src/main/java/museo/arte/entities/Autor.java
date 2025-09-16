package museo.arte.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import museo.arte.entities.ObraArtistica;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor    // Lombok: genera un constructor vacío (OBLIGATORIO para JPA) ✅
@Builder
@Entity
@Table(name = "autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nombre;

    // Relación inversa: un museo tiene muchas obras
    @OneToMany(mappedBy = "autor", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    // Relación 1:N -> Un Autor tiene muchas ObrasArtísticas
    // 'mappedBy = "museo"' significa que la FK está en la entidad ObraArtistica (campo "museo")
    // cascade = CascadeType.PERSIST: si persistís el Museo, también persiste automáticamente sus Obras
    // fetch = FetchType.LAZY: no carga las Obras automáticamente al leer un Museo (solo se cargan al acceder al getter)
    @ToString.Exclude
    private Set<ObraArtistica> obras;

    // public List<ObraArtistica> obras;    
    // realmente la diferencia entre list o set es solo si quiero repetidos o no
}

