package com.example.demo.services;

// Anotaciones de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Tipos de colección y fechas
import java.time.LocalDate;
import java.util.List;

// Modelo y repositorio
import com.example.demo.models.Tarea;
import com.example.demo.repositories.TareaRepository;

@Service // Indica que esta clase forma parte de la capa de lógica de negocio
public class TareaService {

    @Autowired // Inyecta automáticamente el repositorio correspondiente
    private TareaRepository repository;

    // Devuelve todas las tareas almacenadas
    public List<Tarea> obtenerTodas() {
        return repository.findAll();
    }

    // Busca una tarea por su ID o retorna null si no existe
    public Tarea obtenerPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Agrega una nueva tarea, aplicando lógica de negocio previa
    public Tarea crear(Tarea tarea) {
        if (tarea.getFechaLimite() == null) {
            tarea.setFechaLimite(LocalDate.now().plusDays(7)); // Por defecto, 1 semana
        }
        if (tarea.getPrioridad() == null) {
            tarea.setPrioridad(Tarea.Prioridad.MEDIA); // Por defecto, prioridad media
        }
        return repository.save(tarea);
    }
    
    // Actualiza una tarea existente (reemplazo total)
    public Tarea actualizar(Long id, Tarea tarea) {
        tarea.setId(id); // Asegura que el ID sea el mismo que el que se intenta actualizar
        return repository.save(tarea); // save actúa como insert/update según el contexto
    }

    // Elimina una tarea por ID
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    // Consulta tareas vencidas usando el método definido en el repositorio
    public List<Tarea> tareasVencidas() {
        return repository.tareasVencidas(LocalDate.now());
    }
}
   