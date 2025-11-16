package clinica.cita;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/cita")
public class CitaControl {

    @Autowired
    private CitaServicio serv;

    @GetMapping("/listar")
    public List<Cita> listar(){ return serv.listar(); }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id){
        Cita c = serv.buscar(id);
        if (c == null){
            return ResponseEntity.status(404).body(Map.of("message", "Cita no encontrada"));
        }
        return ResponseEntity.ok(c);
    }

    // Entrada de Cita:
    // Con horario: { "dniPaciente": "..." | "pacienteId": Long, "horarioId": Long 1, "idDoctor": "45", "motivo": "Dolor", "tipoCita": "CONSULTA" }    
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody Cita cita ){
        try{

            Cita citaCreada = serv.crear(cita);
            return ResponseEntity.created(URI.create("/cita/buscar/" + citaCreada.getNumero())).body(citaCreada);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (IllegalStateException e){
            return ResponseEntity.status(409).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelar(@PathVariable Long id){
        Cita c = serv.cancelar(id);
        if (c == null){
            return ResponseEntity.status(404).body(Map.of("message", "Cita no encontrada"));
        }
        return ResponseEntity.ok(c);
    }
}
