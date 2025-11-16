package clinica.carritohorario;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173") // habilitar CORS para el frontend en desarrollo
@RestController
@RequestMapping("/carritohorario")
public class CarritoHorarioMedicoControl {
    @Autowired
    private CarritoHorarioMedicoServicio serv;

    @PostMapping("/agregar")
    public ResponseEntity<?>  agregar(@RequestBody Linea horario){
        try{
            Linea lineaAgregada = serv.agregar(horario);
            return ResponseEntity.ok(lineaAgregada);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (IllegalStateException e){
            return ResponseEntity.status(409).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/quitar/{id}")
    public void quitar(@PathVariable Long id){
        serv.quitar(id);
    }

    @GetMapping("/listar")
    public List<Linea> listar(){
        return serv.listar();
    }

    @GetMapping("/total")
    public double total(){
        return serv.total();
    }

    @DeleteMapping("/nuevo")
    public void nuevo(){
        serv.nuevo();
    }
}
