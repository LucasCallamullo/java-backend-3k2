package utnfc.isi.back.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Arrays;

@Entity
@Table(name = "BOARD_GAMES")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_board_game")
    @SequenceGenerator(name = "seq_board_game", sequenceName = "SEQ_BOARD_GAME_ID", allocationSize = 1)
    @Column(name = "ID_GAME")
    private Integer id;

    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    @Column(name = "YEAR_PUBLISHED")
    private Integer yearPublished;

    @Column(name = "MIN_AGE")
    private Integer minAge;

    @Column(name = "AVERAGE_RATING", precision = 3, scale = 2)
    private BigDecimal averageRating;

    @Column(name = "USERS_RATING")
    private Integer usersRating;

    @Column(name = "MIN_PLAYERS")
    private Integer minPlayers;

    @Column(name = "MAX_PLAYERS")
    private Integer maxPlayers;

    // Relaciones con otras tablas
    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_DESIGNER", nullable = false)
    private Designer designer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_PUBLISHER", nullable = false)
    private Publisher publisher;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_CATEGORY", nullable = false)
    private Category category;

    /**
     * Checks whether the given number of players is supported by this game.
     * Si MAX_PLAYERS es null → no hay límite superior.
     * Si MIN_PLAYERS es null → no hay límite inferior.
     * 
     * Ejemplo:
            BoardGame catan = new BoardGame();
            catan.setMinPlayers(3);
            catan.setMaxPlayers(4);

            catan.supportsPlayerCount(2); // false
            catan.supportsPlayerCount(3); // true
            catan.supportsPlayerCount(5); // false
     */
    public boolean supportsPlayerCount(int players) {
        if (players <= 0) return false;

        boolean minOk = (minPlayers == null) || (players >= minPlayers);
        boolean maxOk = (maxPlayers == null) || (players <= maxPlayers);

        return minOk && maxOk;
    }

    /**
     * Checks whether all provided ages are suitable for this game.
     * Si MIN_AGE es null → sin restricción de edad.
     * 
     * Ejemplo:
            BoardGame dixit = new BoardGame();
            dixit.setMinAge(8);

            dixit.isSuitableForAges(new int[]{10, 12, 9}); // true
            dixit.isSuitableForAges(new int[]{6, 9, 11}); // false (hay un menor de 8)
     */
    public boolean isSuitableForAges(int[] ages) {
        if (ages == null || ages.length == 0) return false;
        if (minAge == null) return true;
        return Arrays.stream(ages).allMatch(age -> age >= minAge);
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %d | Nombre: %s | Año: %d | Edad mínima: %d | Jugadores: %d-%d | Rating: %.2f (%d votos) | Diseñador: %s | Editorial: %s | Categoría: %s",
            id,
            name,
            yearPublished,
            minAge,
            minPlayers,
            maxPlayers,
            averageRating != null ? averageRating : BigDecimal.ZERO,
            usersRating != null ? usersRating : 0,
            designer != null ? designer.getName() : "Desconocido",
            publisher != null ? publisher.getName() : "Desconocido",
            category != null ? category.getName() : "Desconocido"
        );
    }
}
