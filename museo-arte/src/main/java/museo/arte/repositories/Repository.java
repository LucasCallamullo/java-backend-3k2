package museo.arte.repositories;

import java.util.Set;
import java.util.List;
import java.util.stream.Stream;

import museo.arte.repositories.context.DbContext;
import jakarta.persistence.EntityManager;

/**
 * Clase abstracta genérica que implementa el patrón Repository.
 *
 * - T = tipo de la entidad (ej: Museo, ObraArtistica, etc.)
 * - K = tipo de la clave primaria (ej: Integer, Long, UUID)
 *
 * El objetivo es separar la lógica de persistencia (acceso a BD)
 * de la lógica de negocio. Las entidades @Entity son solo datos,
 * y los Repository saben cómo acceder/guardar esos datos en la base.
 */
public abstract class Repository<T, K> {

    // EntityManager: interfaz principal de JPA para interactuar con la BD
    // Se obtiene desde un "DbContext" centralizado (DbContext).
    protected EntityManager manager;

    public Repository() {
        // Obtenemos un único EntityManager desde el contexto de la aplicación
        manager = DbContext.getInstance().getManager();
    }

    /**
     * Inserta una nueva entidad en la base de datos.
     * - manager.persist(entity) = marca la entidad como "nueva" y la inserta.
     * - Se abre y cierra una transacción explícitamente.
     */
    public void create(T entity) {
        var transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(entity);  // INSERT en la tabla correspondiente
        transaction.commit();
    }

    /**
     * Actualiza una entidad existente en la base de datos.
     * - manager.merge(entity) = sincroniza el estado del objeto con la BD.
     */
    public void update(T entity) {
        var transaction = manager.getTransaction();
        transaction.begin();
        manager.merge(entity);  // UPDATE en la tabla correspondiente
        transaction.commit();
    }

    /**
     * Elimina una entidad por su id.
     * - Se busca primero con getById (delega en la subclase concreta).
     * - Luego se pasa a remove() para borrarla de la BD.
     */
    public T delete(K id) {
        var transaction = manager.getTransaction();
        transaction.begin();
        var entity = this.getById(id); // implementación concreta en subclase
        manager.remove(entity);        // DELETE en la tabla correspondiente
        transaction.commit();
        return entity;
    }

    /* Read genericos */
    public Set<T> getAllSet() {
        TypedQuery<T> query = manager.createQuery(
            "SELECT e FROM " + getEntityClass().getSimpleName() + " e", getEntityClass()
        );
        return query.getResultList().stream().collect(Collectors.toSet());
    }

    public List<T> getAllList() {
        TypedQuery<T> query = manager.createQuery(
            "SELECT e FROM " + getEntityClass().getSimpleName() + " e", getEntityClass()
        );
        return query.getResultList();
    }

    public Stream<T> getAllStream() {
        TypedQuery<T> query = manager.createQuery(
            "SELECT e FROM " + getEntityClass().getSimpleName() + " e", getEntityClass()
        );
        return query.getResultStream();
    }

    /**
     * Recupera una entidad por su id (PRIMARY KEY).
     * - Abstracto porque depende del tipo de entidad (Museo, Obra, etc.)
     * - Se implementa con manager.find(Entidad.class, id)
     */
    public T getById(K id) {
        // Esto es genérico: usa manager.find() con la clase de la entidad
        return manager.find(getEntityClass(), id);
    }

    /**
     * Método genérico para recuperar una entidad por su campo 'nombre'.
     * - Funciona si la entidad T tiene un atributo llamado 'nombre'.
     * - Devuelve null si no se encuentra ninguna coincidencia.
     */
    public T getByName(String name) {
        // Construimos la query JPQL dinámicamente usando el nombre de la clase T
        String jpql = "SELECT e FROM " + getEntityClass().getSimpleName() + " e WHERE e.nombre = :name";
        
        TypedQuery<T> query = manager.createQuery(jpql, getEntityClass());
        query.setParameter("name", name); // asigna el parámetro ':name' al valor pasado
        List<T> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public boolean existsByName(String name) {
        String jpql = "SELECT COUNT(e) FROM " + getEntityClass().getSimpleName() + " e WHERE e.nombre = :name";
        TypedQuery<Long> query = manager.createQuery(jpql, Long.class);
        query.setParameter("name", name);
        return query.getSingleResult() > 0;
    }

    /**
     * Método abstracto que debe implementar cada subclase
     * para devolver la clase concreta de la entidad T.
     * Esto es necesario para construir queries genéricas.
     */
    protected abstract Class<T> getEntityClass();
}



/**
 * Recupera todas las entidades como un Set (colección completa).
 * - La implementación concreta usará una query JPQL.
 
public abstract Set<T> getAll(); */

/**
 * Recupera todas las entidades como un Stream.
 * - Útil para procesar datos en paralelo o con pipelines de Stream API.
 
public abstract Stream<T> getAllStrem(); */

/**
 * Ejemplo de método "extra" para validar existencia.
 * - Se puede sobreescribir en los repositorios concretos con queries JPQL.
 * - Por defecto devuelve false.

public boolean existsByName(String name) {
    return false;
}; */
