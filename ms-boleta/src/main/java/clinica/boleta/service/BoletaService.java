package clinica.boleta.service;

import clinica.boleta.model.Boleta;
import clinica.boleta.repository.BoletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BoletaService {
    @Autowired
    private BoletaRepository repository;

    public Boleta guardar(Boleta boleta) {
        return repository.save(boleta);
    }

    public List<Boleta> listar() {
        return repository.findAll();
    }

    public Optional<Boleta> buscar(Long id) {
        return repository.findById(id);
    }
    
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
