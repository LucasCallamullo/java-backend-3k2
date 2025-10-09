package utnfc.isi.back.sim.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClasificacionEsrb {

    E("E", "Everyone", "Apto para todas las edades. Sin contenido objetable."),
    E10("E10+", "Everyone 10+", "Apto para mayores de 10 años. Contiene algo más de fantasía o violencia leve."),
    T("T", "Teen", "Apto para mayores de 13 años. Puede incluir violencia moderada, lenguaje fuerte o temas sugerentes."),
    M("M", "Mature", "Apto para mayores de 17 años. Contiene violencia intensa, sangre, temas sexuales o lenguaje fuerte."),
    AO("AO", "Adults Only", "Apto solo para adultos mayores de 18 años. Contiene temas sexuales explícitos o violencia extrema."),
    RP("RP", "Rating Pending", "Clasificación aún pendiente de revisión."),
    UR("UR", "Unrated", "Clasificación indeterminada.");

    private final String codigo;
    private final String nombre;
    private final String descripcion;

    @Override
    public String toString() {
        return codigo;
    }

    /**
     * Devuelve la clasificación correspondiente al código (ej. "E", "M", "AO").
     */
    public static ClasificacionEsrb fromCodigo(String codigo) {
        for (ClasificacionEsrb c : values()) {
            if (c.codigo.equalsIgnoreCase(codigo)) return c;
        }
        throw new IllegalArgumentException("Código de clasificación ESRB inválido: " + codigo);
    }
}

