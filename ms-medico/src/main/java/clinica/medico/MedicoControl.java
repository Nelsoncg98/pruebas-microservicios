package clinica.medico;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
