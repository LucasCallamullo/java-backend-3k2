package com.ejemplo.alquileres;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alquiler {
    private String codigo;
    private int huespedes;
    private int diasAlquiler;
    private double costoPorDia;
    private Cliente cliente;
    private Localizacion direccionPropiedad;

    public double calcularAlquiler() {
        return this.diasAlquiler * this.costoPorDia;
    }
}
