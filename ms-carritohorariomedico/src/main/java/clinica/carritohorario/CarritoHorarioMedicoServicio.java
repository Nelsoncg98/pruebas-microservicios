package clinica.carritohorario;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CarritoHorarioMedicoServicio {
    @Autowired
    private CarritoHorarioMedicoRepositorio repo;
    @Autowired
    private RestTemplate resTem;

    public Linea agregarPorIdHorario(Long horarioId) {

        String url = "http://localhost:8085/horariomedico/buscar/" + horarioId;

        Linea lin = resTem.getForObject(url, Linea.class);
        return repo.save(lin);
    }

    

    public void quitar(Long id) {
        repo.deleteById(id);
    }

    public List<Linea> listar() {
        return repo.findAll();
    }

    public double total() {
        // total sencillo: cantidad de líneas
        return repo.findAll().size();
    }

    public void nuevo() {
        repo.deleteAll();
    }
}
