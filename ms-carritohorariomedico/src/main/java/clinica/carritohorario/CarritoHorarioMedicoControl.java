package clinica.carritohorario;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carritohorario")
public class CarritoHorarioMedicoControl {
    @Autowired
    private CarritoHorarioMedicoServicio serv;

    @PostMapping("/agregar/{horarioId}")
    public Linea agregar(@PathVariable Long horarioId){
        return serv.agregarPorIdHorario(horarioId);
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
