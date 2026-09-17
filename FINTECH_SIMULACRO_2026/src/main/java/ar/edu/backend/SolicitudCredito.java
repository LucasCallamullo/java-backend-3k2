package ar.edu.backend;

import java.util.List;

public class SolicitudCredito {

    private final String idCliente;
    private final String tipoCliente;
    private final double scoreFinanciero;
    private final String estado;
    private final String nivelMorosidad;

    private final static double TASA_BASE = 40;

    public SolicitudCredito(String idClient, String tipoCliente, double scoreFinanciero, String estado, String nivelMorosidad) {

        if (idClient == null || idClient.isBlank() || tipoCliente == null || tipoCliente.isBlank())
            throw new IllegalArgumentException("El Id del Cliente como su Categorizacion (tipo) son requeridos");

        this.idCliente = idClient;
        this.tipoCliente = tipoCliente;
        this.scoreFinanciero = scoreFinanciero;
        this.estado = estado;
        this.nivelMorosidad = nivelMorosidad;
    }

    public SolicitudCredito(String idClient, String tipoCliente, double scoreFinanciero, String estado) {

        if (idClient == null || idClient.isBlank() || tipoCliente == null || tipoCliente.isBlank())
            throw new IllegalArgumentException("El Id del Cliente como su Categorizacion (tipo) son requeridos");

        this.idCliente = idClient;
        this.tipoCliente = tipoCliente;
        this.scoreFinanciero = scoreFinanciero;
        this.estado = estado;
        this.nivelMorosidad = "";
    }

    public static SolicitudCredito desdeCampos(String[] tokens) {
        //    0        1        2        3    4
        // ["CL969","PREMIUM","12868",4384,RECHAZADO, ...]

        // dejenlo asi o preguntenlo como hacer esto sin romper el test 
        if (tokens.length != 5 && tokens.length != 6)
            throw new IllegalArgumentException("Se esperan 5 columnas a procesar");


        double ingresos = Double.parseDouble(tokens[2]);
        double deudas = Double.parseDouble(tokens[3]);
        String estado = tokens[4];
        
        String mora = "";
        if (tokens.length == 6) {
            mora = tokens[5];
            validarEstadosYMora(estado, mora);

            if (mora.equalsIgnoreCase("NORMAL")) {
                validarIngresosDedudasLegacy(ingresos, deudas);
            }

            // EN_MORA se consideran como solicitudes validas si sus ingresos superan 
            // los 25000 y sus deudas no pueden superar el 12% de sus ingresos
            if (mora.equalsIgnoreCase("EN_MORA")) {
                if (ingresos < 25000) {
                    throw new IllegalArgumentException("Los ingresos mensuales no son aptos para solicitar el credito");
                }
                
                double docePercent = ingresos * 0.12;
                if (deudas > docePercent) {
                    throw new IllegalArgumentException("La deudas registradas no permiten solicitar el credito");
                }
            }
        }

        // NORMAL conserva todas las reglas anteriores y sus calculos tal cual estan definidos.
        if (tokens.length == 5) {
            validarIngresosDedudasLegacy(ingresos, deudas);
        }

    
        double scoreFinanciero = (1 - (deudas / ingresos)) * 100;

        // aca crea solicitud credito
        return new SolicitudCredito(tokens[0], tokens[1], scoreFinanciero, tokens[4], mora);
    }

    private static void validarIngresosDedudasLegacy(double ingresos, double deudas) {
        if (ingresos < 1000 || ingresos > 15000)
            throw new IllegalArgumentException("Los ingresos mensuales no son aptos para solicitar el credito");

        if (deudas < 0 || deudas >= ingresos)
            throw new IllegalArgumentException("La deudas registradas no permiten solicitar el credito");
    }


    private static void validarEstadosYMora(String estado, String mora) {

        if (estado == null || mora == null) {
            throw new IllegalArgumentException("estado y mora no pueden ser null");
        }

        estado = estado.toUpperCase();
        mora = mora.toUpperCase();
        
        if (!List.of("APROBADO", "PRE_APROBADO", "RECHAZADO").contains(estado)) {
            throw new IllegalArgumentException("Los profes no laburan");
        }


        // APROBADO, PRE_APROBADO, el sexto campo puede admitir EN_MORA o NORMAL
        if (List.of("APROBADO", "PRE_APROBADO").contains(estado)) {
            if (!List.of("EN_MORA", "NORMAL").contains(mora)) {
                throw new IllegalArgumentException("Error en mora aprobado");
            }
        }

        // las solicitudes RECHAZADO solo adminte EN_MORA
        if (estado.equalsIgnoreCase("RECHAZADO")) {
            if (!mora.equalsIgnoreCase("EN_MORA")) {
                throw new IllegalArgumentException("Error en mora aprobado");
            }
        }
    }



    public String getNivelMorosidad() {
        return this.nivelMorosidad;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public double getScoreFinanciero() {
        return scoreFinanciero;
    }

    public String getEstado() {
        return estado;
    }

    public double getTasaInteresAnual() {
        // 40 + 90 + 0.8 = 112
        return SolicitudCredito.TASA_BASE + (100 - this.scoreFinanciero) * 0.8;
    }

}
