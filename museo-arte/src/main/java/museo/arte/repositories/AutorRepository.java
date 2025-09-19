package museo.arte.repositories;

import museo.arte.entities.Autor;

/**
 * Repositorio específico para la entidad Autor.
 * 
 * Extiende de Repository<T,K> para heredar métodos CRUD genéricos:
 *   - add(T entity)
 *   - update(T entity)
 *   - delete(K id)
 *
 * Aquí se agregan consultas y operaciones propias de la entidad Autor.
 */
public class AutorRepository extends Repository<Autor, Integer> {

    /**
     * Constructor que llama al constructor del Repository genérico
     * para inicializar el EntityManager.
     */
    public AutorRepository() {
        super();
    }

    @Override
    protected Class<Autor> getEntityClass() {
        return Autor.class;
    }
}

/**
 * Recupera todos los autores como un Set.
 * - Ejecuta una consulta JPQL: "SELECT a FROM Autor a"
 * - Convierte la lista resultante en un Set para evitar duplicados.
 *
 * @return Set de todos los autores

@Override
public Set<Autor> getAll() {
    TypedQuery<Autor> query = manager.createQuery("SELECT a FROM Autor a", Autor.class);
    return query.getResultList().stream().collect(Collectors.toSet());
} */

/**
 * Recupera todos los autores como Stream.
 * - Útil si se quieren procesar los resultados con Stream API.
 *
 * @return Stream de todos los autores

@Override
public Stream<Autor> getAllStrem() {
    TypedQuery<Autor> query = manager.createQuery("SELECT a FROM Autor a", Autor.class);
    return query.getResultStream();
} */

/**
 * Método adicional:
 * Verifica si existe un Autor por nombre.
 *
 * @param name nombre del autor
 * @return true si existe un autor con ese nombre, false si no

@Override
public boolean existsByName(String name) {
    // Creamos una query JPQL (Java Persistence Query Language).
    // - "SELECT COUNT(a)" devuelve la cantidad de registros que cumplen la condición.
    // - "FROM Autor a" indica que consultamos la entidad Autor (no la tabla directamente).
    // - "WHERE a.name = :name" filtra por el campo "name" de Autor,
    //   usando un parámetro llamado ":name".
    TypedQuery<Long> query = manager.createQuery(
        "SELECT COUNT(a) FROM Autor a WHERE a.name = :name", Long.class);

    // Vinculamos el parámetro ":name" de la query con el valor recibido en el método.
    // Esto previene SQL Injection y permite reutilizar la query con distintos valores.
    query.setParameter("name", name);

    // Ejecutamos la query y obtenemos un único resultado (COUNT siempre devuelve un número).
    // Si COUNT > 0 → significa que existe al menos un Autor con ese nombre.
    return query.getSingleResult() > 0;
} */

 /**
 * 
 * Recupera un Autor por su nombre.
 * 
 * - Usa JPQL (Java Persistence Query Language), que trabaja con **atributos de la clase**
 *   en vez de nombres de columnas de la base de datos.
 * - En la query: "SELECT a FROM Autor a WHERE a.nombre = :name"
 *   - "a" es un alias para la entidad Autor.
 *   - "nombre" es el **atributo de la clase Autor**, no el nombre de la columna en la BD.
 *   - ":name" es un parámetro que se reemplaza con setParameter().
 * - Se obtiene una lista de resultados. 
 *   - Si está vacía, se retorna null.
 *   - Si hay al menos un resultado, se retorna el primero.
 *
 * @param name Nombre del autor a buscar
 * @return Autor encontrado o null si no existe
 */
/* 
@Override
public Autor getByName(String name) {
    // Crear la query JPQL con parámetro
    TypedQuery<Autor> query = manager.createQuery(
        "SELECT a FROM Autor a WHERE a.nombre = :name", Autor.class
    );

    // Reemplaza ":name" por el valor real pasado al método
    query.setParameter("name", name);

    // Ejecuta la query y obtiene la lista de resultados
    List<Autor> results = query.getResultList();

    // Retorna null si no hay resultados, sino el primero
    return results.isEmpty() ? null : results.get(0);
}
*/