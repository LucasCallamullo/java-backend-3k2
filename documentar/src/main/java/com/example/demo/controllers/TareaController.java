package com.example.demo.controllers;

// Anotaciones de Spring para controladores REST y mapeo de rutas
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.models.Tarea;
import com.example.demo.services.TareaService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController // Indica que esta clase expone endpoints REST
@RequestMapping("/api/tareas") // Prefijo común para todas las rutas de este recurso
public class TareaController {
    @Autowired // Inyección del servicio de lógica de negocio
    private TareaService servicio;

    // GET /api/tareas → lista todas las tareas
    @GetMapping
    public List<Tarea> listar() {
        return servicio.obtenerTodas();
    }

    // GET /api/tareas/{id} → busca una tarea por ID
    @Operation(
        summary = "Buscar tarea por ID",
        description = "Devuelve una tarea específica dado su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Tarea encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Tarea.class)
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Tarea no encontrada"
        )
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    public Tarea obtener(
            @Parameter(description = "ID de la tarea a buscar", required = true)
            @PathVariable @NonNull Long id) {
        return servicio.obtenerPorId(id);
    }


    // POST /api/tareas → crea una nueva tarea
    @PostMapping
    public Tarea crear(@RequestBody Tarea tarea) {
        return servicio.crear(tarea);
    }

    // PUT /api/tareas/{id} → actualiza una tarea existente
    @PutMapping("/{id}")
        public Tarea actualizar(@PathVariable Long id, @RequestBody Tarea tarea) {
        return servicio.actualizar(id, tarea);
    }

    // DELETE /api/tareas/{id} → elimina una tarea por ID
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable @NonNull Long id) {
        servicio.eliminar(id);
    }

    // GET /api/tareas/vencidas → retorna las tareas vencidas
    @GetMapping("/vencidas")
    public List<Tarea> vencidas() {
        return servicio.tareasVencidas();
    }
}
