package com.frc.isi.museo.entidades;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Lombok genera automáticamente los getters, setters, equals, hashCode y toString
@Data 

// Marca la clase como una entidad JPA (se mapeará a una tabla en la BD)
@Entity 

// Lombok: genera un patrón Builder para construir objetos de esta clase de manera más legible
@Builder 

// Lombok: genera un constructor con todos los campos
@AllArgsConstructor 

// Lombok: genera un constructor vacío (obligatorio para JPA)
@NoArgsConstructor 

// NamedQueries: consultas predefinidas (JPQL) que podés invocar por nombre en tu repositorio
@NamedQueries({
    // Busca una obra por su código (id)
    @NamedQuery(
        name = "ObraArtistica.GetById", 
        query = "SELECT o FROM ObraArtistica o WHERE o.codigo = :codigo"
    ),
    // Busca una obra por su nombre
    @NamedQuery(
        name = "ObraArtistica.GetByNombre", 
        query = "SELECT o FROM ObraArtistica o WHERE o.nombre = :nombre"
    )
})
public class ObraArtistica {

    // Marca como clave primaria
    @Id
    // Define el nombre de la columna en la BD
    @Column(name = "id")
    // Estrategia de autogeneración de la PK (Identity = auto-incremental en la BD)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    // Columnas simples (al no tener @Column, JPA las mapea con el mismo nombre de atributo)
    private String nombre;
    private String anio;
    private double montoAsegurado;
    private boolean seguroTotal;

    // Relación muchos-a-uno: muchas obras pueden pertenecer a un mismo museo
    @ManyToOne(
        cascade = CascadeType.PERSIST, // Si persisto una Obra, también persiste el Museo
        fetch = FetchType.EAGER        // Carga el museo automáticamente junto con la Obra
    )
    @JoinColumn(
        name = "museoId",              // Nombre de la FK en la tabla ObraArtistica
        referencedColumnName = "id"    // Columna en la tabla Museo que actúa como PK
    )
    @ToString.Exclude // Lombok: evita incluir "museo" en toString() para prevenir bucles infinitos
    private Museo museo;

    // Relación muchos-a-uno con estilo artístico
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "estiloArtisticoId", referencedColumnName = "id")
    @ToString.Exclude
    private EstiloArtistico estiloArtistico;

    // Relación muchos-a-uno con autor
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "autorId", referencedColumnName = "id")
    @ToString.Exclude
    private Autor autor;
}
