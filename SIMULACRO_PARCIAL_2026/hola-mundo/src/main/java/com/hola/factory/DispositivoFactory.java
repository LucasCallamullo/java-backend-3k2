package com.hola.factory;

import java.util.HashMap;
import java.util.Map;

import com.hola.models.dispositivos.Cerradura;
import com.hola.models.dispositivos.Dispositivo;
import com.hola.models.dispositivos.EnchufeInteligente;
import com.hola.models.dispositivos.LuzInteligente;
import com.hola.models.dispositivos.SensorApertura;
import com.hola.models.dispositivos.SensorMovimiento;
import com.hola.models.dispositivos.Termostato;

public class DispositivoFactory {

    public static Map<String, Dispositivo> mapaDispostivios = new HashMap<>();


    /**
     * Índices del CSV:
     *  0 timestamp
     *  1 deviceId
     *  2 tipoDispositivo
     *  3 ubicacion
     *  4 nombre
     *  5 tipoEvento
     *  6 valor
     *  7 unidad
     *  8 estadoPrevio
     *  9 estadoNuevo
     * 10 severidad
     */
    public static Dispositivo crearDispositivo(String[] lista) {

        if (lista == null || lista.length < 5) {
            throw new IllegalArgumentException("Fila inválida: se esperaban al menos 5 columnas");
        }

        String id = lista[1];
        String tipo = lista[2];
        String ubicacion = lista[3];
        String nombre = lista[4];

        return switch (tipo) {
            case "TERMOSTATO"        -> new Termostato(id, nombre, ubicacion);
            case "LUZ"               -> new LuzInteligente(id, nombre, ubicacion);
            case "ENCHUFE"           -> new EnchufeInteligente(id, nombre, ubicacion);
            case "CERRADURA"         -> new Cerradura(id, nombre, ubicacion);
            case "SENSOR_MOVIMIENTO" -> new SensorMovimiento(id, nombre, ubicacion);
            case "SENSOR_APERTURA"   -> new SensorApertura(id, nombre, ubicacion);
            default -> throw new IllegalArgumentException(
                    "Tipo de dispositivo desconocido: " + tipo);
        };
    }
}