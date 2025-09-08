package com.ejemplo.alquileres;

import org.junit.jupiter.api.*; // Importa anotaciones y clases de JUnit 5
import static org.junit.jupiter.api.Assertions.*; // Importa métodos de aserción


// mvn -Dtest=AlquilerTest test
/* Solo se ejecutarán los métodos @Test que estén en AlquilerTest.java.
No se ejecutan las demás clases de test. */

/*
 * mvn -Dtest=AlquilerTest#testCalcularAlquiler test
Solo se ejecuta el método testCalcularAlquiler() dentro de AlquilerTest.
Útil si querés depurar un test en particular.
 */

public class AlquilerTest {

    @Test
    @DisplayName("Test para probar el método calcular alquiler.")
    void testCalcularAlquiler() {
        Cliente cliente = new Cliente("123", "Juan", "555-1234", "juan@mail.com");
        Localizacion loc = new Localizacion("Calle 1", "Buenos Aires", "1000");
        Alquiler alquiler = new Alquiler(
            "A001", 2, 5, 100.0, cliente, loc
        );

        assertEquals(500.0, alquiler.calcularAlquiler());
    }
}
