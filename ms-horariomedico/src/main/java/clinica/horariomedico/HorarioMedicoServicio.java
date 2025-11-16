package clinica.horariomedico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HorarioMedicoServicio {
    @Autowired
    private HorarioMedicoRepositorio repo;
    

    public List<HorarioMedico> listar() {
        return repo.findAll();
    }

    public HorarioMedico guardar(HorarioMedico h) {
        if (h.getMedicoId() == null) {
            throw new IllegalArgumentException("El campo medicoId es obligatorio");
        }
        // Valor por defecto robusto: si no viene en el payload, marcar disponible=true
        if (h.getDisponible() == null) {
            h.setDisponible(Boolean.TRUE);
        }
        return repo.save(h);
    }

    public HorarioMedico buscar(Long id) {
        Optional<HorarioMedico> op = repo.findById(id);
        return op.orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public HorarioMedico actualizar(Long id, HorarioMedico h) {
        h.setNumero(id);
        return repo.save(h);
    }

    public void limpiar() {
        repo.deleteAll();
    }

    // Cambia el flag disponible=false si está true; lanza IllegalStateException si ya no está disponible
    public HorarioMedico reservar(Long id){
        Optional<HorarioMedico> op = repo.findById(id);
        if (op.isEmpty()) return null; // 404 en el controlador
        HorarioMedico h = op.get();
        if (Boolean.FALSE.equals(h.getDisponible())){
            throw new IllegalStateException("El horario ya está reservado/no disponible");
        }
        h.setDisponible(Boolean.FALSE);
        return repo.save(h);
    }

    // Cambia el flag disponible=true si está false; lanza IllegalStateException si ya está disponible
    public HorarioMedico liberar(Long id){
        Optional<HorarioMedico> op = repo.findById(id);
        if (op.isEmpty()) return null; // 404 en el controlador
        HorarioMedico h = op.get();
        if (Boolean.TRUE.equals(h.getDisponible())){
            throw new IllegalStateException("El horario ya está disponible");
        }
        h.setDisponible(Boolean.TRUE);
        return repo.save(h);
    }

}
