package ar.edu.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserSolicitudes {

    private static final String CABECERA_CINCO = "idCliente,tipoCliente,ingresosMensuales,totalDeudasActuales,estadoSolicitud";
    private static final String CABECERA_SEIS = "idCliente,tipoCliente,ingresosMensuales,totalDeudasActuales,estadoSolicitud,nivelMorosidad";

    public ResultadoProceso procesar(Path archivo) throws IOException {

        // 1. declarar las variables a retornar o con las cuales va a formar el objeto final 
        ArrayList<SolicitudCredito> creditos = new ArrayList<>();
        ArrayList<String> errores = new ArrayList<>();
        ArrayList<String> descartes = new ArrayList<>();
        int leidas = 0;

        // leer archivo csv
        try (BufferedReader lector = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {

            // idCliente,tipoCliente,ingresosMensuales,totalDeudasActuales,estadoSolicitud
            String cabecera = lector.readLine();        // para no traernos mas de una linea al leer el encabezado

            if (!CABECERA_CINCO.equals(cabecera) && !CABECERA_SEIS.equals(cabecera)) {
                // tirar arrojar 
                throw new IllegalArgumentException("encabezado incorrecto");
            }

            String linea;
            while ((linea = lector.readLine()) != null) {
                leidas++;
                int numero = leidas + 1;

                try {
                    // la ia que viva la pepa
                    // ["CL969","PREMIUM","12868",4384,RECHAZADO]
                    String[] tokens = Arrays.stream(linea.split(",", -1)).map(String::strip).toArray(String[]::new);

                    // agregada verificaciones de cantidad de tokens
                    if (CABECERA_CINCO.equals(cabecera) && tokens.length != 5)
                        throw new IllegalArgumentException("Cantidad de columnas incorrectas");

                    if (CABECERA_SEIS.equals(cabecera) && tokens.length != 6)
                        throw new IllegalArgumentException("Cantidad de columnas incorrectas");

                    //    0    1        2    3        4
                    // CL969,PREMIUM,12868,4384,RECHAZADO
                    if (tokens[1].equalsIgnoreCase("black")) {
                        descartes.add("Linea %d: BLACK".formatted(numero));
                        continue;
                    }

                    // compara si el estado es igual a algun valor de la lista
                    if (!List.of("REGULAR", "PREMIUM", "PR", "P", "R").contains(tokens[1]))
                        throw new IllegalArgumentException("estado desconocido: " + tokens[1]);

                    creditos.add(SolicitudCredito.desdeCampos(tokens));

                } catch (IllegalArgumentException ex) {
                    errores.add("Linea %d: %s".formatted(numero, ex.getMessage()));
                }
            }
        }

        return new ResultadoProceso(creditos, leidas, descartes.size(), errores, descartes);

    }
}
