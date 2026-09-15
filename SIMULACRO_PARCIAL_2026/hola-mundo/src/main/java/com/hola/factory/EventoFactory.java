package com.hola.factory;

import com.hola.models.eventos.*;

import java.time.LocalDateTime;
import java.util.Optional;

public class EventoFactory {

    /**
     * Índices del CSV:
     *  0 timestamp        -> LocalDateTime
     *  1 deviceId         -> String
     *  2 tipoDispositivo  -> String
     *  3 ubicacion        -> String
     *  4 nombre           -> String
     *  5 tipoEvento       -> discrimina la subclase
     *  6 valor            -> double (puede venir vacío)
     *  7 unidad           -> String (Wh, C, %, ...)
     *  8 estadoPrevio     -> String (APAGADO, TRABADA, ...)
     *  9 estadoNuevo      -> String
     * 10 severidad        -> String (ALTA, MEDIA, BAJA)
     */
    public static Evento crearEvento(String[] lista) {

        if (lista == null || lista.length < 11) {
            throw new IllegalArgumentException("Fila inválida: se esperaban 11 columnas");
        }

        // --- Campos comunes a toda subclase de Evento ---
        LocalDateTime timestamp = LocalDateTime.parse(lista[0]);
        String deviceId = lista[1];
        String tipoDispositivo = lista[2];
        String ubicacion = lista[3];
        String nombre = lista[4];

        // --- Discriminador ---
        String tipoEvento = lista[5];

        // --- Campos específicos (pueden venir vacíos) ---
        double valor = parsearDoubleOpcional(lista[6]);        // permite valores null --> 0.0
        String unidad = vacioANull(lista[7]);
        String estadoPrevio = vacioANull(lista[8]);
        String estadoNuevo = vacioANull(lista[9]);
    
        // una regla de negocio dice que el estado nuevo y el actual no pueden ser iguales
        // if (estadoPrevio == estadoNuevo) return null;


        String severidad = vacioANull(lista[10]);

        return switch (tipoEvento) {
            case "MEDICION" -> new EventoMedicion(
                    timestamp, deviceId, tipoDispositivo, ubicacion, nombre,
                    valor, unidad);

            case "CAMBIO_ESTADO" -> new EventoCambioEstado(
                    timestamp, deviceId, tipoDispositivo, ubicacion, nombre,
                    estadoPrevio, estadoNuevo);

            case "COMANDO" -> new EventoComando(
                    timestamp, deviceId, tipoDispositivo, ubicacion, nombre, estadoNuevo);

            case "ALERTA" -> new EventoAlerta(
                    timestamp, deviceId, tipoDispositivo, ubicacion, nombre,
                    estadoNuevo, severidad);

            default -> throw new IllegalArgumentException(
                    "Tipo de evento desconocido: " + tipoEvento);
        };
    }

    // --- Helpers ---

    private static double parsearDoubleOpcional(String s) {
        if (s == null || s.isBlank()) return 0.0;
        return Double.parseDouble(s);
    }

    private static String vacioANull(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }
}