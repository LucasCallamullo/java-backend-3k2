package pre.enunciado.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
// import lombok.ToString;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "JUEGOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JUEGO_ID")
    private Integer juegoId;

    @Column(name = "TITULO", nullable = false)
    private String titulo;

    @Column(name = "FECHA_LANZAMIENTO")
    private Integer fechaLanzamiento;

    @Column(name = "CLASIFICACION_ESRB", length = 4)
    private String clasificacionEsrb;

    @Column(name = "RATING")
    private Double rating;

    @Column(name = "JUEGOS_FINALIZADOS")
    private Integer juegosFinalizados;

    @Column(name = "JUGANDO")
    private Integer jugando;

    @Lob
    @Column(name = "RESUMEN", nullable = false)
    private String resumen;

    /*
     * Relación Many-to-One con Desarrollador:
     * Un Juego pertenece a un único Desarrollador,
     * pero un mismo Desarrollador puede haber creado muchos Juegos.
     */
    @ManyToOne(
        cascade = CascadeType.PERSIST, // Indica que si persistimos un Juego nuevo con un Desarrollador no persistido,
        // también se persistirá automáticamente ese Desarrollador.
        // ⚠️ Solo aplica a persistencia (no a remove, merge, etc.).
        // En muchos casos se usa CascadeType.ALL, pero depende del diseño.

        fetch = FetchType.EAGER        // Estrategia de carga: EAGER = siempre trae al Desarrollador junto con el Juego.
        // Alternativa: LAZY (traería al Desarrollador solo cuando se invoque el getter).
    )
    @JoinColumn(
        name = "DESARROLLADOR_ID",     // Nombre de la columna FK en la tabla JUEGOS.
        // Es la columna que conecta con DESARROLLADORES(DESA_ID).

        nullable = false,              // No se permite NULL en esta columna. 
        // Cada Juego debe tener obligatoriamente un Desarrollador.

        referencedColumnName = "DESA_ID"  // Nombre de la columna PK en la tabla DESARROLLADORES a la que apunta la FK.
        // En este caso, DESA_ID es la PK. 
        // Este parámetro es opcional: si lo omitís, JPA ya toma la PK por defecto.
    )
    // @ToString.Exclude                // Recomendación: evitar incluirlo en toString() generado por Lombok,
    // ya que podría causar bucles infinitos si hay relaciones bidireccionales.
    private Desarrollador desarrollador;

    // Relaciones Many-to-One
    @ManyToOne(
        cascade = CascadeType.PERSIST,
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "GENERO_ID")
    private Genero genero;


    @ManyToOne(
        cascade = CascadeType.PERSIST,
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "PLATAFORMA_ID")
    private Plataforma plataforma;

}
