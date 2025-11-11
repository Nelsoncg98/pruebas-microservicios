package clinica.paciente;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = "http://localhost:5173") // habilitar CORS para el frontend en desarrollo
@RestController
@RequestMapping("/paciente")
public class PacienteControl {
    @Autowired
    private PacienteServicio serv;

    @PostMapping("/guardar")
    public Paciente guardar(@RequestBody Paciente p){ return serv.guardar(p); }
    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id){ serv.eliminar(id); }
    @GetMapping("/listar")
    public List<Paciente> listar(){ return serv.listar(); }
    @GetMapping("/buscar/{id}")
    public Paciente buscar(@PathVariable Long id){ return serv.buscar(id); }

    // GET /paciente?dni=XXXXXXXX
    @GetMapping(params = "dni")
    public ResponseEntity<?> buscarPorDni(@RequestParam String dni){
        Paciente p = serv.buscarPorDni(dni);
        if (p == null){
            return ResponseEntity.status(404).body(Map.of("message", "Paciente no encontrado"));
        }
        return ResponseEntity.ok(p);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Paciente p){
        Paciente actual = serv.actualizar(id, p);
        if (actual == null){
            return ResponseEntity.status(404).body(Map.of("message", "Paciente no encontrado"));
        }
        return ResponseEntity.ok(actual);
    }
    @DeleteMapping("/limpiar")
    public void limpiar(){ serv.limpiar(); }
}
