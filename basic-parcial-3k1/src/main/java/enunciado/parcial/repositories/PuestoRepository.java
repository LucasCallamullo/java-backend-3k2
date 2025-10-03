package enunciado.parcial.repositories;

/* import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream; */

import enunciado.parcial.entities.Puesto;

public class PuestoRepository extends Repository<Puesto, Integer> {

    public PuestoRepository() {
        super();
    }

    @Override
    protected Class<Puesto> getEntityClass(){
        return Puesto.class;
    }
}
    /* 
    @Override
    public Puesto getById(Integer id) {
        // Observen esto se hace directamente sin usar queies
        return this.manager.find(Puesto.class, id);
    }

    @Override
    public Set<Puesto> getAllSet() {
        return this.manager.createQuery("SELECT a FROM Puesto a", Puesto.class)
                .getResultList()
                .stream().collect(Collectors.toSet());
    }

    @Override
    public Stream<Puesto> getAllStrem() {
        return this.manager.createQuery("SELECT o FROM Puesto o", Puesto.class).getResultStream();
    }

    @Override
    public Puesto getByName(String name) {
        // Se crea una consulta nombrada (NamedQuery) definida en la entidad Puesto
        // It creates a NamedQuery defined in the Puesto entity
        return this.manager.createNamedQuery("Puesto.GetByNombre", Puesto.class) 

                // Se asigna el valor al parámetro ":nombre" de la consulta con el valor recibido en "name"
                // Sets the value of the ":nombre" parameter in the query with the provided "name"
                .setParameter("nombre", name) 

                // Ejecuta la consulta y devuelve los resultados como un Stream (no como lista)
                // Executes the query and returns the results as a Stream (instead of a list)
                .getResultStream() 

                // Busca un resultado cualquiera del Stream (envuelto en Optional)
                // Retrieves any result from the Stream (wrapped in Optional)
                .findAny() 

                // Verifica si ese Optional tiene valor (true si encontró, false si no)
                // Checks if the Optional contains a value (true if found, false if not)
                .isPresent(); 
    }
    */
