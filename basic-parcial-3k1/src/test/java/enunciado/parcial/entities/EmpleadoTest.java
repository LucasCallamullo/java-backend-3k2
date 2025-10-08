package enunciado.parcial.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EmpleadoTest {

    @Test
    @DisplayName("Debe calcular correctamente el salario final para empleados fijos")
    void testCalcularSalarioFinalEmpleadoFijo() {
        // Arrange (preparación)
        Empleado empleado = Empleado.builder()
                .nombre("Juan Pérez")
                .edad(30)
                .fechaIngreso(LocalDate.of(2020, 1, 1))
                .salario(1000.0)
                .empleadoFijo(true)
                .build();

        // Act (ejecución)
        double salarioFinal = empleado.calcularSalarioFinal();

        // Assert (verificación)
        assertEquals(1080.0, salarioFinal, 0.01, 
            "El salario final del empleado fijo debería ser 8% mayor");
    }

    @Test
    @DisplayName("Debe calcular correctamente el salario final para empleados NO fijos")
    void testCalcularSalarioFinalEmpleadoNoFijo() {
        // Arrange
        Empleado empleado = Empleado.builder()
                .nombre("María López")
                .edad(25)
                .fechaIngreso(LocalDate.of(2022, 5, 10))
                .salario(1200.0)
                .empleadoFijo(false)
                .build();

        // Act
        double salarioFinal = empleado.calcularSalarioFinal();

        // Assert
        assertEquals(1200.0, salarioFinal, 0.01,
            "El salario final del empleado no fijo debería ser igual al salario base");
    }

    @Test
    @DisplayName("Debe construir correctamente un empleado usando el Builder")
    void testBuilderCreaEmpleadoCorrectamente() {
        // Arrange
        LocalDate fechaIngreso = LocalDate.of(2021, 3, 15);

        // Act
        Empleado empleado = Empleado.builder()
                .id(1)
                .nombre("Carlos Gómez")
                .edad(40)
                .fechaIngreso(fechaIngreso)
                .salario(1500.0)
                .empleadoFijo(true)
                .build();

        // Assert
        assertAll(
            () -> assertEquals(1, empleado.getId()),
            () -> assertEquals("Carlos Gómez", empleado.getNombre()),
            () -> assertEquals(40, empleado.getEdad()),
            () -> assertEquals(fechaIngreso, empleado.getFechaIngreso()),
            () -> assertTrue(empleado.isEmpleadoFijo())
        );
    }
}
