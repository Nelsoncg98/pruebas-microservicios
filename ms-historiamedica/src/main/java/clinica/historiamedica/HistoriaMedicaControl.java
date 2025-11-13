
package clinica.historiamedica;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = "http://localhost:5173") // habilitar CORS para el frontend en desarrollo
@RestController
@RequestMapping("/historiaMedica") // Ruta base del microservicio
public class HistoriaMedicaControl {

    @Autowired
    private HistoriaMedicaServicio servicio;

    // Listar todas las historias médicas
    @GetMapping("/listar")
    public List<HistoriaMedica> listar() {
        return servicio.listar();
    }

    // Guardar nueva historia médica
    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody HistoriaMedica historia) {
        try {
            HistoriaMedica h = servicio.guardar(historia);
            return ResponseEntity.status(HttpStatus.CREATED).body(h);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        }
    }

    // Buscar historia médica por id de historia
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        HistoriaMedica h = servicio.buscar(id);
        if (h != null) {
            return ResponseEntity.ok(h);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Historia médica no encontrada"));
        }
    }

    // Actualizar historia médica
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody HistoriaMedica historia) {
        try {
            HistoriaMedica h = servicio.actualizar(id, historia);
            if (h != null) {
                return ResponseEntity.ok(h);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "Historia médica no encontrada"));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        }
    }

    // Buscar historia médica por pacienteId
    @GetMapping("/buscar/paciente/{pacienteId}")
    public ResponseEntity<?> buscarPorPaciente(@PathVariable Long pacienteId) {
        try {
            HistoriaMedica h = servicio.buscarPorPacienteId(pacienteId);
            return ResponseEntity.ok(h);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", e.getMessage()));
        }
    }
}
