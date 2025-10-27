package clinica.programacionmedica;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProgramacionMedicaServicio {
    @Autowired
    private ProgramacionMedicaRepositorio repo;

    @Autowired
    private RestTemplate resTem;
    
    private String fecha(){
        Date dat=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dat);
    }


    public ProgramacionMedica nueva( Long idAdministrativo){
        List<Linea> horarios = resTem.getForObject("http://localhost:8094/carritohorario/listar", List.class);
        
        ProgramacionMedica p = new ProgramacionMedica( idAdministrativo, fecha(), true, horarios);
        
        return p;
    }

    

    public ProgramacionMedica buscar(Long id){
        Optional<ProgramacionMedica> op = repo.findById(id);
        return op.orElse(null);
    }

    public List<ProgramacionMedica> listar(){
        return repo.findAll();
    }

    

    public void eliminar(Long id){
        // Inactivar en lugar de eliminar físicamente
        ProgramacionMedica p = buscar(id);
        if (p != null){
            p.setActivo(false);
            repo.save(p);
        }
    }

    public void limpiar(){
        repo.deleteAll();
    }

    public ProgramacionMedica reactivar(Long id){
        ProgramacionMedica p = buscar(id);
        if (p == null) return null;
        p.setActivo(true);
        return repo.save(p);
    }
}
