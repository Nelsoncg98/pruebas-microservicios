package clinica.programacionmedica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public boolean existeAdministrativo(Long idAdministrativo){
        String url = "http://localhost:8081/personaladministrativo/buscar/" + idAdministrativo;
        Object resp = resTem.getForObject(url, Object.class);
        return resp != null;
    }


    public ProgramacionMedica nueva( Long idAdministrativo){
        if (!existeAdministrativo(idAdministrativo)){
            throw new IllegalArgumentException("El administrativo con id=" + idAdministrativo + " no existe");
        }

        List<Linea> horarios = resTem.getForObject("http://localhost:8094/carritohorario/listar", List.class);
        List<Linea> lineasguardadas = new ArrayList<>();

        // guardar los horarios medicos
        for (Linea h : horarios) {
            HorarioMedico guardado = resTem.postForObject("http://localhost:8085/horariomedico/guardar", h, HorarioMedico.class);

            h.setNumero(guardado.getNumero());
            lineasguardadas.add(h);
        }

    ProgramacionMedica p = new ProgramacionMedica( idAdministrativo, fecha(), true, lineasguardadas);
    // Persistir la programación antes de limpiar el carrito
    ProgramacionMedica guardada = repo.save(p);
    resTem.delete("http://localhost:8094/carritohorario/nuevo");

    return guardada;
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
