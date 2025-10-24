package clinica.medico;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoServicio {
    @Autowired
    private MedicoRepositorio repo;

    public List<Medico> listar(){
        return repo.findAll();
    }

    public Medico guardar(Medico m){
        return repo.save(m);
    }

    public Medico buscar(Long id){
        Optional<Medico> op = repo.findById(id);
        return op.orElse(null);
    }

    public void eliminar(Long id){
        repo.deleteById(id);
    }

    public void limpiar(){
        repo.deleteAll();
    }
}
