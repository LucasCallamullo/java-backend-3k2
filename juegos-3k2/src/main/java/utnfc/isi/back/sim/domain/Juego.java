package utnfc.isi.back.sim.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "JUEGOS")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Juego {
    @Id
    @SequenceGenerator(name="seq_juego", sequenceName="SEQ_JUEGOS", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_juego")
    @Column(name = "JUEGO_ID")
    private Integer id;

    @Column(name = "TITULO", nullable = false, length = 255)
    private String titulo;

    @Column(name = "FECHA_LANZAMIENTO")
    private Integer fechaLanzamiento;

    @ManyToOne(nullable = true)    // optional = true, 
    @JoinColumn(name = "GENERO_ID")
    private Genero genero;

    @ManyToOne(nullable = true)
    @JoinColumn(name = "DESARROLLADOR_ID")
    private Desarrollador desarrollador;

    @ManyToOne(nullable = true)
    @JoinColumn(name = "PLATAFORMA_ID")
    private Plataforma plataforma;

    @Enumerated(EnumType.STRING)
    @Column(name = "CLASIFICACION_ESRB", length = 4)
    private ClasificacionEsrb clasificacionEsrb;

    @Column(name = "RATING")
    private Double rating;

    @Column(name = "JUEGOS_FINALIZADOS")
    private Integer juegosFinalizados;

    @Column(name = "JUGANDO")
    private Integer jugando;

    // @Lob
    @Column(name = "RESUMEN", nullable = true)
    private String resumen;
}
