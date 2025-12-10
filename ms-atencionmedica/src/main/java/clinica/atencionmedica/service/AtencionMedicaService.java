package clinica.atencionmedica.service;

import clinica.atencionmedica.model.AtencionMedica;
import clinica.atencionmedica.repository.AtencionMedicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtencionMedicaService {

    @Autowired
    private AtencionMedicaRepository repository;

    public AtencionMedica guardar(AtencionMedica atencion) {
        return repository.save(atencion);
    }

    public Optional<AtencionMedica> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<AtencionMedica> listarTodas() {
        return repository.findAll();
    }
    
    public List<AtencionMedica> buscarPorCita(Long idCita) {
        return repository.findByIdCita(idCita);
    }

    public AtencionMedica actualizarReceta(Long id, String recetaRef) {
        return repository.findById(id).map(atencion -> {
            atencion.setReceta(recetaRef);
            return repository.save(atencion);
        }).orElse(null);
    }

    public AtencionMedica actualizarAnalisis(Long id, String analisisRef) {
        return repository.findById(id).map(atencion -> {
            atencion.setAnalisisClinico(analisisRef);
            return repository.save(atencion);
        }).orElse(null);
    }
}
