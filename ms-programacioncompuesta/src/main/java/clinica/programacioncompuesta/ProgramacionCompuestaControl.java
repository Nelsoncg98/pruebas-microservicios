package clinica.programacioncompuesta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/programacioncompuesta")
public class ProgramacionCompuestaControl {

    @Autowired
    private ProgramacionCompuestaServicio serv;

    // Similar a /programacionmedica/nueva pero orquestado desde un MS sin BD
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@RequestParam Long idAdministrativo){
        try{
            Object resultado = serv.nuevaProgramacion(idAdministrativo);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (IllegalStateException e){
            return ResponseEntity.status(409).body(Map.of("message", e.getMessage()));
        }
    }

    // Obtener una programación médica enriquecida con sus horarios
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscar(@org.springframework.web.bind.annotation.PathVariable Long id){
        try{
            Object resultado = serv.buscarProgramacion(id);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(404).body(Map.of("message", e.getMessage()));
        }
    }
}
