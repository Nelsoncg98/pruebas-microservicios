package clinica.programacionmedica;


import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173") // habilitar CORS para el frontend en desarrollo
@RestController
@RequestMapping("/programacionmedica")
public class ProgramacionMedicaControl {
    @Autowired
    private ProgramacionMedicaServicio serv;

    

    @PostMapping("/nueva/{idAdministrativo}")
    public ResponseEntity<?> nueva(@PathVariable Long idAdministrativo) {
        try {
            ProgramacionMedica p = serv.nueva(idAdministrativo);
            return ResponseEntity.status(201).body(p);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of(
                "message", "Error al crear programación médica",
                "error", e.getMessage()
            ));
        }
    }

    

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        ProgramacionMedica p = serv.buscar(id);
        if (p == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Programación no encontrada"));
        }
        return ResponseEntity.ok(p);
    }

    // http://localhost:8086/programacionmedica/listar?page=0&size=5
    // paginacion para menorizar la carga de datos y optimizar peticiones de enriquecimiento
    @GetMapping("/listar")
    public ResponseEntity<Page<ProgramacionMedica>> listar(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(serv.listar(page, size));
    }

    @PostMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        ProgramacionMedica p = serv.buscar(id);
        if (p == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Programación no encontrada"));
        }
        serv.eliminar(id);
        return ResponseEntity.ok(Map.of("message", "Programación inactivada"));
    }

    @PostMapping("/limpiar")
    public ResponseEntity<Void> limpiar() {
        serv.limpiar();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reactivar/{id}")
    public ResponseEntity<?> reactivar(@PathVariable Long id) {
        ProgramacionMedica p = serv.reactivar(id);
        if (p == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Programación no encontrada"));
        }
        return ResponseEntity.ok(p);
    }
}