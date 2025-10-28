package clinica.medico;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173") // habilitar CORS para el frontend en desarrollo
@RestController
@RequestMapping("/medico")
public class MedicoControl {
    @Autowired
    private MedicoServicio serv;

    @PostMapping("/guardar")
    public Medico guardar(@RequestBody Medico m){
        return serv.guardar(m);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id){
        serv.eliminar(id);
    }

    @GetMapping("/listar")
    public List<Medico> listar(){
        return serv.listar();
    }

    @GetMapping("/porEspecialidad")
    public ResponseEntity<?> porEspecialidad(@RequestParam(required = true) String especialidad){
        if (especialidad == null || especialidad.isBlank()){
            return ResponseEntity.badRequest().body(Map.of("message", "El parámetro 'especialidad' es requerido"));
        }
        return ResponseEntity.ok(serv.buscarPorEspecialidad(especialidad));
    }

    @GetMapping("/buscar/{id}")
    public Medico buscar(@PathVariable Long id){
        return serv.buscar(id);
    }

    @DeleteMapping("/limpiar")
    public void limpiar(){
        serv.limpiar();
    }

    @PostMapping("/reactivar/{id}")
    public void reactivar(@PathVariable Long id){
        serv.reactivar(id);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Medico m){
        Medico actual = serv.actualizar(id, m);
        if (actual == null){
            return ResponseEntity.status(404).body(Map.of("message", "Medico no encontrado"));
        }
        return ResponseEntity.ok(actual);
    }
}
