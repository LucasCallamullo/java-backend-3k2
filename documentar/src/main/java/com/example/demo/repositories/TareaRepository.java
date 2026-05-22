package com.example.demo.repositories;

// Importaciones necesarias para JpaRepository y queries personalizadas
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Tarea;

import java.time.LocalDate;
import java.util.List;
@Repository // (opcional, pero explícito y claro)
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    // Consulta personalizada con JPQL para obtener tareas vencidas (fecha pasada y no completadas)
    @Query("SELECT t FROM Tarea t WHERE t.fechaLimite < :fecha AND t.completada = false")
    List<Tarea> tareasVencidas(@Param("fecha") LocalDate fecha);
}
