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

    public Paciente buscarPorDni(String dni){
        Optional<Paciente> op = repo.findByDni(dni);
        return op.orElse(null);
    }

    // Actualizar un paciente existente; devuelve la entidad actualizada o null si no existe
    public Paciente actualizar(Long id, Paciente p){
        Optional<Paciente> op = repo.findById(id);
        if (op.isEmpty()) return null;
        Paciente existente = op.get();
        existente.setNombre(p.getNombre());
        existente.setApellido(p.getApellido());
    existente.setDni(p.getDni());
        existente.setFechaNacimiento(p.getFechaNacimiento());
        existente.setTelefono(p.getTelefono());
        existente.setEmail(p.getEmail());
        existente.setDireccion(p.getDireccion());
        existente.setEstado(p.isEstado());
        return repo.save(existente);
    }
}
