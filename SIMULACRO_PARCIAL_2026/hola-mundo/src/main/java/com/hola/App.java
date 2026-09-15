package com.hola;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hola.factory.ParseadorCsv;
import com.hola.models.dispositivos.Cerradura;
import com.hola.models.dispositivos.Dispositivo;
import com.hola.models.dispositivos.EnchufeInteligente;
import com.hola.models.dispositivos.Termostato;
import com.hola.models.eventos.Evento;
import com.hola.services.DispositivoService;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Map<String, Dispositivo> mapaDispositivos = new HashMap<>();
        List<Evento> listaEventos = new ArrayList<>();
        
        ParseadorCsv.leerCsv("eventos.csv", ";", mapaDispositivos, listaEventos);
        
        System.out.println("Dispositivos: " + mapaDispositivos.size());
        System.out.println("Eventos: " + listaEventos.size());

        // actividad uno
        Map<String, Integer> mapaActividadUno = DispositivoService.eventosPorDispositivo(mapaDispositivos);
        
        mapaActividadUno.forEach((key, value) -> {
            System.out.println(key + " → " + value);
        });

    }
}

/* 

        Dispositivo a = new Cerradura(null, null, null);
        Dispositivo b = new EnchufeInteligente(null, null, null);
        Dispositivo c = new Termostato(null, null, null);

        List<Dispositivo> lista = List.of(a, b, c);

        lista.forEach(d -> {
            System.out.println(d.descripcionEstado());
            
            if (d instanceof Cerradura) {
                var x = (Cerradura) d;
                System.out.println(x.soyCerradura());
            }
        });
*/