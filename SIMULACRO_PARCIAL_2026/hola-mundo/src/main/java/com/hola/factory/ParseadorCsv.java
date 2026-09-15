package com.hola.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.hola.models.dispositivos.Dispositivo;
import com.hola.models.eventos.Evento;

public class ParseadorCsv {

    /**
     * Lee un archivo CSV desde resources, saltea el encabezado y devuelve
     * cada línea como una lista de strings separados por el delimitador dado.
     *
     * @param nombreArchivo Ruta dentro de resources, ej: "eventos.csv"
     * @param delimitador   Separador de columnas, ej: "," o ";"
     * @return Lista de filas; cada fila es una List<String> con las columnas.
     */
    public static void leerCsv(String nombreArchivo,
                           String delimitador,
                           Map<String, Dispositivo> mapaDispositivos,
                           List<Evento> listaEventos) {

        // El "/" inicial es clave para que ClassLoader lo busque en resources
        try (InputStream is = ParseadorCsv.class.getResourceAsStream("/" + nombreArchivo)) {

            if (is == null) {
                throw new IllegalArgumentException(
                        "No se encontró el archivo en resources: " + nombreArchivo);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8))) {

                String linea;
                boolean esEncabezado = true;

                while ((linea = br.readLine()) != null) {

                    // Saltear encabezado
                    if (esEncabezado) {
                        esEncabezado = false;
                        continue;
                    }

                    // Ignorar líneas vacías
                    if (linea.isBlank()) {
                        continue;
                    }

                    // Separar por el delimitador (con -1 para no perder columnas vacías)
                    // "2026-09-05T07:00:00;term-liv-01;TERMOSTATO;Living;Termostato Living;MEDICION;22.3;C;;;"

                    String[] columnas = linea.split(delimitador, -1);

                    // ! FIJENSE ESTO
                    String deviceId = columnas[1];

                    /* // !  Map<String, Dispositivo> mapaDispositivos
                    + mapa = {
                    +     "term-liv-01": Termostato
                    +     "cerr-front-01": Cerradura
                    +     "cerr-gar-01": Cerradura
                    + } 
                    +
                    */
                    Dispositivo dispositivo;

                    if (mapaDispositivos.containsKey(deviceId)) {
                        dispositivo = mapaDispositivos.get(deviceId);
                    } else {
                        dispositivo = DispositivoFactory.crearDispositivo(columnas);
                        mapaDispositivos.put(deviceId, dispositivo);
                    }

                    Evento ev = EventoFactory.crearEvento(columnas);

                    if (ev == null) continue;

                    dispositivo.agregarEvento(ev);
                    listaEventos.add(ev);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error leyendo el CSV: " + nombreArchivo, e);
        }
    }
}