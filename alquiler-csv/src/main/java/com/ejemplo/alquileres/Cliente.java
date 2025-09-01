package com.ejemplo.alquileres;

import lombok.Data;

@Data        // no incluye constructores
public class Cliente {
    private String dni;
    private String nombre;
    private String telefono;
    private String email;

    public Cliente(String dni, String nombre, String telefono, String email){
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }
}
