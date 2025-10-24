package clinica.paciente;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteServicio {
    @Autowired
    private PacienteRepositorio repo;

    public Paciente guardar(Paciente p){ return repo.save(p); }
    public void eliminar(Long id){ repo.deleteById(id); }
    public List<Paciente> listar(){ return repo.findAll(); }
    public Paciente buscar(Long id){ Optional<Paciente> op = repo.findById(id); return op.orElse(null);} 
    public void limpiar(){ repo.deleteAll(); }
}
