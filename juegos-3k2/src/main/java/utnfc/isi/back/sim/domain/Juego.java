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
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;

@Entity
@Table(name = "JUEGOS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Juego {

    @Id
    @SequenceGenerator(name = "seq_juego", sequenceName = "SEQ_JUEGOS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_juego")
    @Column(name = "JUEGO_ID")
    private Integer id;

    @Column(name = "TITULO", nullable = false, length = 255)
    private String titulo;

    @Column(name = "FECHA_LANZAMIENTO")
    private Integer fechaLanzamiento;

    @ManyToOne(optional = true)
    @JoinColumn(name = "GENERO_ID")
    private Genero genero;

    @ManyToOne(optional = true)
    @JoinColumn(name = "DESARROLLADOR_ID")
    private Desarrollador desarrollador;

    @ManyToOne(optional = true)
    @JoinColumn(name = "PLATAFORMA_ID")
    private Plataforma plataforma;

    @Column(name = "RATING")
    private Double rating;

    @Column(name = "JUEGOS_FINALIZADOS")
    private Integer juegosFinalizados;

    @Column(name = "JUGANDO")
    private Integer jugando;

    @Lob
    @Column(name = "RESUMEN", nullable = false)
    private String resumen;

    // @Enumerated(EnumType.STRING)
    // @Column(name = "CLASIFICACION_ESRB", length = 4)
    // private ClasificacionEsrb clasificacionEsrb;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "CLASIFICACION_ESRB", length = 4)
    private String clasificacionEsrbCode;

    /**
     * API “amigable”: trabajar con Enum
     */
    @Transient
    public ClasificacionEsrb getClasificacionEsrb() {
        return clasificacionEsrbCode == null ? null : ClasificacionEsrb.fromCodigo(clasificacionEsrbCode);
    }

    public void setClasificacionEsrb(ClasificacionEsrb esrb) {
        this.clasificacionEsrbCode = (esrb == null ? null : esrb.getCodigo());
    }

    /**
     * Si alguna vez querés setear directo el código (tests/import)
     */
    public void setClasificacionEsrbCode(String code) {
        this.clasificacionEsrbCode = (code == null || code.isBlank()) ? null : code.trim();
    }
}
