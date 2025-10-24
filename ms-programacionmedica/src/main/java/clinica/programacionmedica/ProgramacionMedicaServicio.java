package clinica.programacionmedica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgramacionMedicaServicio {
    @Autowired
    private ProgramacionMedicaRepositorio repo;
    // Limpio: sin llamados remotos ni validaciones cruzadas aquí

    public ProgramacionMedica crear(ProgramacionMedica p){
        if (p.getFechaProgramacion() == null){
            p.setFechaProgramacion(LocalDateTime.now());
        }
        if (p.getHorariosIds() == null){
            p.setHorariosIds(new ArrayList<>());
        }
        return repo.save(p);
    }

    public ProgramacionMedica buscar(Long id){
        Optional<ProgramacionMedica> op = repo.findById(id);
        return op.orElse(null);
    }

    public List<ProgramacionMedica> listar(){
        return repo.findAll();
    }

    public ProgramacionMedica agregarLineas(Long id, List<Long> nuevos){
        ProgramacionMedica p = buscar(id);
        if (p == null) return null;
        if (nuevos == null || nuevos.isEmpty()) return p;
        // evita duplicados de forma simple
        Set<Long> set = new HashSet<>(p.getHorariosIds());
        set.addAll(nuevos);
        p.setHorariosIds(new ArrayList<>(set));
        return repo.save(p);
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
