package com.frc.isi.museo.entidades;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@NamedQueries({
        @NamedQuery(name = "Museo.GetAll", query = "SELECT a FROM Museo a"),
        @NamedQuery(name = "Museo.GetByNombre", query = "SELECT m FROM Museo m WHERE m.nombre = :nombre")
})
public class Museo {

    @Id // Marca este campo como la Primary Key en la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // Estrategia de autoincremento en la DB (ej: en H2, MySQL: AUTO_INCREMENT)
    private int id;

    private String nombre;

    @OneToMany(mappedBy = "museo", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    // Relación 1:N -> Un Museo tiene muchas ObrasArtísticas
    // 'mappedBy = "museo"' significa que la FK está en la entidad ObraArtistica (campo "museo")
    // cascade = CascadeType.PERSIST: si persistís el Museo, también persiste automáticamente sus Obras
    // fetch = FetchType.LAZY: no carga las Obras automáticamente al leer un Museo (solo se cargan al acceder al getter)
    @ToString.Exclude
    // Evita que Lombok incluya 'obras' en el método toString()
    // (porque podría causar recursión infinita: Museo -> Obra -> Museo -> Obra ...)
    private Set<ObraArtistica> obras;
}


