package clinica.programacionmedica;

import java.net.URI;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/programacionmedica")
public class ProgramacionMedicaControl {
    @Autowired
    private ProgramacionMedicaServicio serv;

    

    @PostMapping("/nueva/{idAdministrativo}")
    public ResponseEntity<ProgramacionMedica> nueva(@PathVariable Long idAdministrativo) {
        try {
            ProgramacionMedica p = serv.nueva(idAdministrativo);
            return ResponseEntity.status(201).body(p);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
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

    @GetMapping("/listar")
    public ResponseEntity<List<ProgramacionMedica>> listar() {
        return ResponseEntity.ok(serv.listar());
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