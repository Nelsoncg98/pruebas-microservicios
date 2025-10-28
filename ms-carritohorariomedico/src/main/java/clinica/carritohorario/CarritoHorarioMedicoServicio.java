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


    public Linea agregar(Linea horario) {
        String url = "http://ms-medico/medico/buscar/" + horario.getMedicoId();
        Object resp = resTem.getForObject(url, Object.class);
        if (resp == null) {
            throw new IllegalArgumentException("El médico con id=" + horario.getMedicoId() + " no existe en ms-medico");
        }

        return repo.save(horario);
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
