package clinica.enfermera;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnfermeraServicio {
    @Autowired
    private EnfermeraRepositorio repo;

    public Enfermera guardar(Enfermera e) {
        return repo.save(e);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public List<Enfermera> listar() {
        return repo.findAll();
    }

    public Enfermera buscar(Long id) {
        Optional<Enfermera> op = repo.findById(id);
        return op.orElse(null);
    }

    public void limpiar() {
        repo.deleteAll();
    }

    public Enfermera actualizar(Long id, Enfermera e) {
        Optional<Enfermera> op = repo.findById(id);
        if (op.isEmpty())
            return null;
        Enfermera existente = op.get();
        existente.setNombre(e.getNombre());
        existente.setApellido(e.getApellido());
        existente.setArea(e.getArea());
        existente.setTurno(e.getTurno());
        existente.setEstado(e.isEstado());
        return repo.save(existente);
    }
}
