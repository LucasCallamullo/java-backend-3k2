package com.ejemplo.alquileres;

import lombok.Data;

/**
 * Representa una localización geográfica con dirección, ciudad y código postal.
 * Cada localización tiene un código único autogenerado.
 */
@Data
public class Localizacion {

    /** Contador compartido para generar códigos únicos automáticamente. */
    private static int contador = 1;

    /** Código único de la localización, autogenerado. */
    private int codigo;

    /** Dirección de la localización. */
    private String direccion;

    /** Ciudad de la localización. */
    private String ciudad;

    /** Código postal de la localización. */
    private String codigoPostal;

    /**
     * Constructor por defecto. Inicializa el código autogenerado.
     */
    public Localizacion() {
        this.codigo = contador++;
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param direccion la dirección de la localización
     * @param ciudad la ciudad de la localización
     * @param codigoPostal el código postal de la localización
     */
    public Localizacion(String direccion, String ciudad, String codigoPostal) {
        this.codigo = contador++;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
    }
}

