package utnfc.isi.back;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utnfc.isi.back.entities.*;
import utnfc.isi.back.infra.DbInitializer;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class BoardGamePersistenceTest {

    private static EntityManagerFactory emf;

    @BeforeAll
    static void initDatabase() {
        // Ejecuta el script SQL (crea tablas y secuencias)
        DbInitializer.recreateSchema();

        // Crea el EntityManagerFactory (usa persistence.xml)
        emf = Persistence.createEntityManagerFactory("boardgames");
    }

    @Test
    void testInsertBoardGameWithRelations() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // --- Crear entidades relacionadas ---
        Designer designer = Designer.builder().name("Reiner Knizia").build();
        Publisher publisher = Publisher.builder().name("Kosmos").build();
        Category category = Category.builder().name("Strategy").build();

        // Persistir primero las dependencias (porque BoardGame tiene foreign keys)
        em.persist(designer);
        em.persist(publisher);
        em.persist(category);

        // --- Crear y persistir el juego de mesa ---
        BoardGame game = BoardGame.builder()
                .name("Tigris & Euphrates")
                .yearPublished(1997)
                .minAge(12)
                .averageRating(BigDecimal.valueOf(8.25))
                .usersRating(12000)
                .minPlayers(2)
                .maxPlayers(4)
                .designer(designer)
                .publisher(publisher)
                .category(category)
                .build();

        em.persist(game);
        em.getTransaction().commit();

        // --- Validar que se haya persistido correctamente ---
        assertNotNull(game.getId(), "El ID del juego no debería ser nulo");
        assertEquals("Tigris & Euphrates", game.getName());
        assertEquals("Reiner Knizia", game.getDesigner().getName());

        // Recuperar el juego desde la base para verificar la relación
        BoardGame dbGame = em.find(BoardGame.class, game.getId());
        assertNotNull(dbGame);
        assertEquals("Kosmos", dbGame.getPublisher().getName());

        System.out.println(dbGame);

        em.close();
    }
}
