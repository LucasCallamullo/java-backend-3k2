package enunciado.parcial.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDate;

@Entity
@Table(name = "empleado")
@Data
@NoArgsConstructor        // ES OBLIGATORIO POR JPA
@AllArgsConstructor
@Builder
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private int edad;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(nullable = false)
    private double salario;

    @Column(name = "empleado_fijo", nullable = false)
    private boolean empleadoFijo;

    // Relación con Departamento (Muchos empleados -> Un departamento)
    @ManyToOne(fetch = FetchType.EAGER) 
    // @ManyToOne indica una relación "muchos a uno".
    // fetch = EAGER significa que el Departamento se carga inmediatamente junto con el Empleado.
    // Es decir, cada vez que traigas un Empleado, su Departamento estará disponible sin necesidad de otra consulta.
    @JoinColumn(
        name = "departamento_id", 
        foreignKey = @ForeignKey(name = "fk_departamento")
    )
    // @JoinColumn indica que en la tabla "empleado" existirá una columna llamada "departamento_id".
    // Esta columna será la FOREIGN KEY que referencia al campo "id" de la tabla "departamento".
    // El atributo foreignKey le da un nombre explícito a la restricción (fk_departamento).
    private Departamento departamento;


    // Relación con Puesto (Muchos empleados -> Un puesto)
    @ManyToOne(fetch = FetchType.LAZY)
    // @ManyToOne define también una relación "muchos a uno".
    // fetch = LAZY significa que el Puesto NO se carga inmediatamente con el Empleado.
    // Solo se consulta en la base de datos cuando realmente se accede a e.getPuesto().
    @JoinColumn(
        name = "puesto_id", 
        foreignKey = @ForeignKey(name = "fk_puesto")
    )
    // @JoinColumn crea la columna "puesto_id" en la tabla "empleado".
    // Esta columna es una FOREIGN KEY que apunta al campo "id" de la tabla "puesto".
    // La restricción en la base de datos tendrá el nombre "fk_puesto".
    private Puesto puesto;


    // Método de lógica de negocio
    public double calcularSalarioFinal() {
        return this.empleadoFijo ? salario * 1.08 : salario;
    }
}

