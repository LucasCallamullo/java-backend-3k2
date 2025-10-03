package enunciado.parcial.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import enunciado.parcial.services.interfaces.IService;
import enunciado.parcial.entities.Empleado;
import enunciado.parcial.repositories.EmpleadoRepository;


public class EmpleadoService implements IService<Empleado, Integer> {

    private final EmpleadoRepository repository;
    private final DepartamentoService departamentoService;
    private final PuestoService puestoService;

    public EmpleadoService() {
        this.repository = new EmpleadoRepository();
        departamentoService = new DepartamentoService();
        puestoService = new PuestoService();
    }

    /**
     * Recupera un autor por ID.
     * - Devuelve null si no existe.
     */
    @Override
    public Empleado getById(Integer id) {
        return repository.getById(id);
    }

    /**
     * Recupera un autor por nombre, y si no existe lo crea.
     */
    @Override
    public Empleado getOrCreateByName(String nombre) {
        Empleado d = repository.getByName(nombre);
        if (d == null) {
            d = new Empleado();
            d.setNombre(nombre);
            repository.create(d);
        }
        return d;
    }

    /**
     * Devuelve todos los autores como lista.
     */
    @Override
    public List<Empleado> getAll() {
        return repository.getAllList();  // usa el genérico
    }

    public void bulkInsert(File fileToImport) throws IOException {
        Files.lines(Paths.get(fileToImport.toURI()))
                .skip(1) // saltear cabecera
                .forEach(linea -> {
                    Empleado emp = this.procesarLinea(linea);
                    this.repository.create(emp);
                });
    }

    private Empleado procesarLinea(String linea) {
        // nombre,edad,fecha_ingreso,salario,empleado_fijo,departamento,puesto
        //  0       1         2        3        4            5            6
        String[] tokens = linea.split(",");

        Empleado empleado = new Empleado();
        empleado.setNombre(tokens[0]);

        // para convetir valores enteros
        empleado.setEdad(Integer.parseInt(tokens[1]));

        // para convertir fechas
        LocalDate fecha = LocalDate.parse(tokens[2]);
        empleado.setFechaIngreso(fecha);

        // para convertir a double
        empleado.setSalario(Double.parseDouble(tokens[3]));

        // para convertir a booleano es
        empleado.setEmpleadoFijo(tokens[4].equalsIgnoreCase("true"));

        String nombre = tokens[5];
        var depa = departamentoService.getOrCreateByName(nombre);
        empleado.setDepartamento(depa);

        nombre = tokens[6];
        var puesto = puestoService.getOrCreateByName(nombre);
        empleado.setPuesto(puesto);

        return empleado;
    }
}
