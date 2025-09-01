package com.ejemplo.alquileres;

/// import static org.junit.jupiter.api.Assertions.assertEquals;
// import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*; // Importa anotaciones y clases de JUnit 5
import static org.junit.jupiter.api.Assertions.*; // Importa métodos de aserción


// Importar tus clases del proyecto
import utils.MyArray;


public class AppTest {

    @Test
    void testBusquedaSecuencial() {
        MyArray<Alquiler> lista = new MyArray<>(5);
        Localizacion loc1 = new Localizacion("Calle1", "BsAs", "1000");
        Localizacion loc2 = new Localizacion("Calle2", "Cordoba", "5000");

        lista.append(
            new Alquiler("A1", 2, 3, 100, 
            new Cliente("1","A","T","E"), loc1)
        );
        lista.append(
            new Alquiler("A2", 3, 2, 200, 
            new Cliente("2","B","T","E"), loc2)
        );

        int count = App.busquedaSecuencial(lista, "BsAs");
        assertEquals(1, count);
    }
    
}
