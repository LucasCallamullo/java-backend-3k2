package ar.edu.backend;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CarteraCreditos {
    private final List<SolicitudCredito> creditos;

    public CarteraCreditos(List<SolicitudCredito> creditos) {
        this.creditos = List.copyOf(creditos);
    }

    public List<SolicitudCredito> filtrar(Predicate<SolicitudCredito> criterio) {
        return this.creditos.stream().filter(criterio).toList();
    }

    /**
     * Metodo que determina o calcula cual es la tasa de interes anual promedio que
     * se agregara a todos
     * los creditos que se encuentran en cartera
     * 
     * @return Valor promedio
     */
    public double tasaInteresPromedio() {

        // this.creditos = [S1, s2, s3]
        // listaDoubles = [ 112.0, ...  ]
        // 112 / 1 = 112.0

        return this.creditos.stream()
                .mapToDouble(SolicitudCredito::getTasaInteresAnual)
                .average()        // calcula un promedio
                .orElse(0);
    }

    /* 
    Incorporar en CarteraCreditos un conteo por Nivel de Morosidad. Puede elegir el nombre y 
    representación de esa operación; para una colección vacía debe dar un resultado vacío o conteos cero.
    */
    public Map<String, Integer> profesMantenseYGrabenloProqueNopuedeserqueseantanvagos() {
        Map<String, Integer> mapita = new HashMap<>();
        mapita.put("EN_MORA", 0);
        mapita.put("NORMAL", 0);

        var listMora = this.creditos.stream()
            .filter(soli -> soli.getNivelMorosidad().equalsIgnoreCase("EN_MORA"))
            .toList();

        var listNormal = this.creditos.stream()
            .filter(soli -> soli.getNivelMorosidad().equalsIgnoreCase("NORMAL"))
            .toList();

        mapita.put("EN_MORA", listMora.size());
        mapita.put("NORMAL", listNormal.size());

        return mapita;
    }



    /**
     * Metodo que determinar cual es la tasa de interes maxima que se ha cobrado por
     * cada tipo de cliente
     * que se contiene en la cartera de creditos
     */
    public Map<String, Double> maximaTasaInteresPorTipo() {
        return this.creditos.stream()
                .collect(Collectors.groupingBy(
                        SolicitudCredito::getTipoCliente,
                        Collectors.collectingAndThen(
                                Collectors.mapping(
                                        SolicitudCredito::getTasaInteresAnual,
                                        Collectors.maxBy(Comparator.naturalOrder())),
                                optionalMax -> optionalMax.orElse(0.0))));

    }

    /**
     * Metodo que informa cual es la tasa promedio que se cobra, cual es la tasa
     * maxima de cada tipo de cliente
     * que se contiene y la cantidad de creditos cuya tasa de interes, en base a su
     * score financiero es mayor un 60%
     */
    public String informe() {
        String cadena = "Tasa Interes Promedio: %.2f | Maximos Tipo de Cliente: %s | Cantidad Cliente 60%%: %d";
        double ti = this.tasaInteresPromedio();
        int cantidad = this.filtrar(sc -> sc.getTasaInteresAnual() > 60).size();
        Map<String, String> tiposFormateados = maximaTasaInteresPorTipo().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> String.format("%.2f", e.getValue()),
                        (newKey, newValue) -> newKey,
                        LinkedHashMap::new));

        return cadena.formatted(ti, tiposFormateados, cantidad);

    }

}
