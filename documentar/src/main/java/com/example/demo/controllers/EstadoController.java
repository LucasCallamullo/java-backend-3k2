package com.example.demo.controllers;

// Imports necesarios
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class EstadoController {
    // Inyección del valor del puerto configurado en la aplicación
    @Value("${server.port}")
    private String puerto;
    
    // Endpoint de estado del servidor
    @GetMapping("/estado")
    public String estado() {
        return "Servidor funcionando correctamente en el puerto " + puerto + ".";
    }

    // Endpoint de saludo personalizado
    @GetMapping("/saludo")
    public String saludo(@RequestParam(defaultValue = "Anónimo") String nombre) {
        return "Hola " + nombre + ", que tengas un día excelente!";
    }
}

