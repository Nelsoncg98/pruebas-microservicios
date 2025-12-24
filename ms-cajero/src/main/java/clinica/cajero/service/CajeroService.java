package clinica.cajero.service;

import clinica.cajero.model.Cajero;
import clinica.cajero.repository.CajeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CajeroService {
    @Autowired
    private CajeroRepository repository;

    public Cajero guardar(Cajero cajero) {
        return repository.save(cajero);
    }
    
    public List<Cajero> listar() {
        return repository.findAll();
    }
    
    public Optional<Cajero> buscar(Long id) {
        return repository.findById(id);
    }
    
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
