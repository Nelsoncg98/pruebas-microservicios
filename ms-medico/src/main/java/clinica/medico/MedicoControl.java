package clinica.medico;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
