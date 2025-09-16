package museo.arte.entities;

import jakarta.persistence.*; // Importamos las anotaciones de JPA
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data        // Lombok genera automáticamente los getters, setters, equals, hashCode y toString
@Builder     // Lombok: genera un patrón Builder para construir objetos de esta clase de manera más legible
@AllArgsConstructor     // Lombok: genera un constructor con todos los campos
@NoArgsConstructor     // Lombok: genera un constructor vacío (OBLIGATORIO para JPA) ✅
@Entity // Marca esta clase como una entidad gestionada por JPA (se convierte en tabla)
@Table(name = "obra_artistica") // Nombre de la tabla en la BD
public class ObraArtistica {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // El ID se autogenera (auto-incremental en la BD)
    private int id;

    @Column(nullable = false, length = 100) 
    // Se define la columna 'nombre', NOT NULL y máx. 100 caracteres
    private String nombre;

    @Column(length = 10) 
    // Año en formato string (ejemplo "1995" o "Siglo XX")
    private String anio;

    @Column(name = "monto_asegurado") 
    // Especificamos un nombre de columna diferente
    private double montoAsegurado;

    @Column(name = "seguro_total")
    private boolean seguroTotal;

    // Relación muchos-a-uno: muchas obras pueden pertenecer a un mismo museo
    @ManyToOne(
        // Si persisto una Obra, también persiste el Museo
        // Persistir es crear una nueva fila en la BDD, si no pusieramos esto, habría que crear antes Museo
        // y despues agregarlo, pero con persist se crea al mismo tiempo que agregamos
        cascade = CascadeType.PERSIST, 

        // al cargar una Obra, trae con sigo consultas hacia Museo, Autor, etc, lo que hace que use mas memoria
        // y si de el problema de n + 1 en cantidad de queries, seria mejor utilizar LAZY
        fetch = FetchType.EAGER        // Carga el museo automáticamente junto con la Obra
    )
    @JoinColumn(
        name = "museo_id",              // 'museo_id' será la FK en la tabla obras_artisticas
        nullable = false,
        referencedColumnName = "id"    // Columna en la tabla Museo que actúa como PK (valor por default FK ID)
    )
    @ToString.Exclude // Lombok: evita incluir "museo" en toString() para prevenir bucles infinitos
    private Museo museo;

    // Relación muchos-a-uno con estilo artístico
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "estilo_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private EstiloArtistico estilo;

    // Relación muchos-a-uno con autor
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private Autor autor;

}



/*
@Data
public class ObraArtistica {

    private int id;
    private String nombre;
    private String anio;
    private double montoAsegurado;
    private boolean seguroTotal;
    private Museo museo;
    private Autor autor;
    private EstiloArtistico estilo;
} */
