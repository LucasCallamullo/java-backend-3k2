package juegos.entities;

/*
 * name: Nombre del juego (Texto)
yearpublished: Año de publicación del juego (Entero)
minplayers: Cantidad mínima de jugadores (Entero)
maxplayers: Cantidad máxima de jugadores (Entero)
rating: Puntaje dado por los usuarios (en el rango 0-10) (Flotante)
designer: Nombre del diseñador del juego. Si no tiene un diseñador conocido figura como (Uncredited)
publisher: Nombre de la editorial que publica el juego

 * 
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data            // getter setter tostring
@AllArgsConstructor
@NoArgsConstructor    // Lombok: genera un constructor vacío (OBLIGATORIO para JPA) ✅
@Builder
@Entity
@Table(name = "game")
public class Game {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // @Column(nullable = false, length = 100)
    private String name;

    private int yearpublished;
    private int minplayers;
    private int maxplayers;
    private double rating;

    // Relación muchos-a-uno: muchas obras pueden pertenecer a un mismo museo
    @ManyToOne(
        // Si persisto una Obra, también persiste el Museo
        // Persistir es crear una nueva fila en la BDD, si no pusieramos esto, habría que crear antes Museo
        // y despues agregarlo, pero con persist se crea al mismo tiempo que agregamos
        cascade = CascadeType.PERSIST, 

        // al cargar una Obra, trae con sigo consultas hacia Museo, Autor, etc, lo que hace que use mas memoria
        // y si de el problema de n + 1 en cantidad de queries, seria mejor utilizar LAZY
        fetch = FetchType.EAGER        // Carga el museo automáticamente junto con la Obra
    )
    @JoinColumn(
        name = "designer_id",              // 'museo_id' será la FK en la tabla obras_artisticas
        nullable = false,
        referencedColumnName = "id"    // Columna en la tabla Museo que actúa como PK (valor por default FK ID)
    )
    @ToString.Exclude             // Lombok: evita incluir "museo" en toString() para prevenir bucles infinitos
    private Designer designer;


    @ManyToOne(
        cascade = CascadeType.PERSIST, 
        fetch = FetchType.EAGER
    )
    @JoinColumn(
        name = "publisher_id",  
        nullable = false,
        referencedColumnName = "id" 
    )
    @ToString.Exclude
    private Publisher publisher;

}
