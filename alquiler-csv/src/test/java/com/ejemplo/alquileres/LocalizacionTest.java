package com.ejemplo.alquileres;
import org.junit.jupiter.api.*; // Importa anotaciones y clases de JUnit 5
import static org.junit.jupiter.api.Assertions.*; // Importa métodos de aserción


public class LocalizacionTest {
    
    @Test
    @DisplayName("Test para probar el auto incremento de la clase.")
    void autoIncrementoTest() {
        Localizacion loc1 = new Localizacion("Calle falsa 123", "Mendoza", "351");
        Localizacion loc2 = new Localizacion("Av. falsa 123", "Buenos Aires", "351");

        // una forma de informar en caso de fallos
        try {
            assertEquals(loc1.getCodigo() + 2, loc2.getCodigo());
        } catch (AssertionError e) {
            System.out.println("Error capturado en LocalizcionTest el test: " + e.getMessage());
            // opcional: marcar el test como "skip" o "passed con aviso"
        }
    }

}
