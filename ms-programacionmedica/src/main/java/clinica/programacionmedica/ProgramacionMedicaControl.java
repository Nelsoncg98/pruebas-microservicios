package clinica.programacionmedica; // Paquete del microservicio de Programación Médica

import java.net.URI; // Para construir Location en respuestas 201
import java.util.List; // Para listas en cuerpos y respuestas
import java.util.Map; // Para respuestas simples tipo {"message": "..."}
import org.springframework.beans.factory.annotation.Autowired; // Inyección de dependencias
import org.springframework.http.ResponseEntity; // Envoltura de respuestas HTTP con código/headers/body
import org.springframework.web.bind.annotation.GetMapping; // Mapeo GET
import org.springframework.web.bind.annotation.PathVariable; // Extraer variables de ruta
import org.springframework.web.bind.annotation.PostMapping; // Mapeo POST
import org.springframework.web.bind.annotation.RequestBody; // Leer body JSON
import org.springframework.web.bind.annotation.RequestMapping; // Prefijo de ruta del controlador
import org.springframework.web.bind.annotation.RestController; // Estereotipo de controlador REST

@RestController // Indica que expone endpoints REST y serializa a JSON automáticamente
@RequestMapping("/programacionmedica") // Prefijo base para todos los endpoints de este controlador
public class ProgramacionMedicaControl {
    @Autowired // Inyecta el servicio de dominio
    private ProgramacionMedicaServicio serv; // Capa de negocio para Programación Médica

    @PostMapping("/crear") // Crea una nueva programación mínima (con administrativoId, fecha y horariosIds)
    public ResponseEntity<?> crear(@RequestBody ProgramacionMedica p){ // Body: JSON de ProgramacionMedica
        try{ // Bloque para capturar validaciones simples
            ProgramacionMedica saved = serv.crear(p); // Delegamos creación al servicio
            // 201 Created con Location apuntando al recurso creado
            return ResponseEntity.created(URI.create("/programacionmedica/buscar/"+saved.getId())).body(saved);
        } catch (IllegalArgumentException e){ // En caso de datos inválidos
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage())); // 400 Bad Request
        }
    }

    @PostMapping("/agregar-lineas/{id}") // Agrega IDs de horarios a la programación (evita duplicados)
    public ResponseEntity<?> agregar(@PathVariable Long id, @RequestBody Map<String, List<Long>> body){ // Body: { "horariosIds": [1,2,3] }
        try{ // Manejo de validaciones sencillas
            List<Long> ids = body.getOrDefault("horariosIds", List.of()); // Obtiene lista de IDs, o lista vacía si no viene
            ProgramacionMedica updated = serv.agregarLineas(id, ids); // Agrega y retorna la programación actualizada
            if (updated == null){ // Si no existe la programación
                return ResponseEntity.status(404).body(Map.of("message", "Programación no encontrada")); // 404 Not Found
            }
            return ResponseEntity.ok(updated); // 200 OK
        } catch (IllegalArgumentException e){ // Datos inválidos
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage())); // 400
        } catch (IllegalStateException e){ // Conflictos de estado (reservado/no disponible) si en un futuro se valida
            return ResponseEntity.status(409).body(Map.of("message", e.getMessage())); // 409 Conflict
        }
    }

    

    @GetMapping("/buscar/{id}") // Obtiene una programación por su ID
    public ResponseEntity<?> buscar(@PathVariable Long id){ // {id} viene en la ruta
        ProgramacionMedica p = serv.buscar(id); // Busca en BD
        if (p == null){ // No encontrada
            return ResponseEntity.status(404).body(Map.of("message", "Programación no encontrada")); // 404
        }
        return ResponseEntity.ok(p); // 200 con la programación
    }

    @GetMapping("/listar") // Lista todas las programaciones (activas e inactivas)
    public ResponseEntity<List<ProgramacionMedica>> listar(){
        return ResponseEntity.ok(serv.listar()); // 200 con array JSON
    }

    

    @PostMapping("/eliminar/{id}") // Inactiva (soft delete) en lugar de borrar físicamente
    public ResponseEntity<?> eliminar(@PathVariable Long id){ // {id} de la programación
        ProgramacionMedica p = serv.buscar(id); // Verifica existencia
        if (p == null){ // No existe
            return ResponseEntity.status(404).body(Map.of("message", "Programación no encontrada")); // 404
        }
        serv.eliminar(id); // Marca activo=false
        return ResponseEntity.ok(Map.of("message", "Programación inactivada")); // 200 OK con mensaje
    }

    @PostMapping("/limpiar") // Borra todos los registros (solo para uso académico/dev)
    public ResponseEntity<Void> limpiar(){
        serv.limpiar(); // Elimina todos en BD
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PostMapping("/reactivar/{id}") // Reactiva una programación inactiva
    public ResponseEntity<?> reactivar(@PathVariable Long id){ // {id} objetivo
        ProgramacionMedica p = serv.reactivar(id); // Cambia activo=true
        if (p == null){ // No existe
            return ResponseEntity.status(404).body(Map.of("message", "Programación no encontrada")); // 404
        }
        return ResponseEntity.ok(p); // 200 con el recurso actualizado
    }
}
