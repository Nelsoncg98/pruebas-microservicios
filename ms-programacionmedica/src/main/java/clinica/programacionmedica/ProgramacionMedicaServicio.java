package clinica.programacionmedica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgramacionMedicaServicio {
    @Autowired
    private ProgramacionMedicaRepositorio repo;

    // Guardado sencillo para uso del microservicio compuesto
    public ProgramacionMedica guardar(ProgramacionMedica p){
        return repo.save(p);
    }

    public ProgramacionMedica buscar(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<ProgramacionMedica> listar() {
        return repo.findAll();
    }

    public void eliminar(Long id) {
        // Inactivar en lugar de eliminar físicamente
        ProgramacionMedica p = buscar(id);
        if (p != null) {
            p.setActivo(false);
            repo.save(p);
        }
    }

    public void limpiar() {
        repo.deleteAll();
    }

    public ProgramacionMedica reactivar(Long id) {
        ProgramacionMedica p = buscar(id);
        if (p == null)
            return null;
        p.setActivo(true);
        return repo.save(p);
    }
}
