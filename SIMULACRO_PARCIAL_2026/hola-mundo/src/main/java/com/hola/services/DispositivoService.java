package com.hola.services;

import com.hola.models.dispositivos.Dispositivo;
import com.hola.models.dispositivos.Termostato;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class DispositivoService {

    /**
     * Devuelve un mapa: nombre del dispositivo -> cantidad de eventos.
     * Ej: { "Termostato Living" -> 24, "Enchufe TV" -> 18, ... }
     */
    public static Map<String, Integer> eventosPorDispositivoImperativo(
            Map<String, Dispositivo> mapaDispositivos) {

        // 1) Creamos un "cuaderno vacío" donde vamos a anotar los resultados
        Map<String, Integer> resultado = new HashMap<>();

        // 2) Recorremos TODOS los dispositivos que nos dieron
        for (Dispositivo dispositivo : mapaDispositivos.values()) {

            // 3) ¿Cómo se llama este dispositivo?
            String nombre = dispositivo.getNombre();

            // 4) ¿Cuántos eventos tiene guardados?
            int cantidad = dispositivo.getEventos().size();

            // 5) Lo anotamos en el cuaderno
            resultado.put(nombre, cantidad);
        }

        // 6) Devolvemos el cuaderno lleno
        return resultado;
    }

    /**
     * Devuelve un mapa: nombre del dispositivo -> cantidad de eventos.
     * Ej: { "Termostato Living" -> 24, "Enchufe TV" -> 18, ... }
     */

    public static Map<String, Integer> eventosPorDispositivo(
            Map<String, Dispositivo> mapaDispositivos) {

        return mapaDispositivos.values().stream()
                .collect(Collectors.toMap(
                        Dispositivo::getNombre,                    // la clave: el nombre
                        // d -> d.getNombre(),                    // la clave: el nombre
                        d -> d.getEventos().size()             // el valor: cuántos eventos
                ));
    }


    // 3 — Temperatura promedio por termostato
    public static Map<String, Double> temperaturaPromedioPorTermostato(
            Map<String, Dispositivo> mapaDispositivos) {
        
        return mapaDispositivos.values().stream()
                .filter(d -> d instanceof Termostato)                 // 1) solo termostatos
                .map(d -> (Termostato) d)                              // 2) casteamos a Termostato
                .collect(Collectors.toMap(
                        Termostato::getNombre,                         // clave: nombre
                        Termostato::temperaturaPromedio                // valor: promedio
                ));
    }

    // 3 — Temperatura promedio por termostato de solo aquellos termostato que tuvieron una temperatura ´promedio mayor a 20
    public static Map<String, Double> actividadInventada(Map<String, Dispositivo> mapa) {

        return mapa.values().stream()
            .filter(d -> d instanceof Termostato)
            .map(d -> (Termostato) d)
            .filter(t -> t.temperaturaPromedio() > 100)
            .collect(Collectors.toMap(
                t -> t.getNombre(), 
                t -> t.temperaturaPromedio())
            );
    }
}