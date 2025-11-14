package clinica.historiamedica;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class HistoriaMedicaServicio {
    @Autowired
    private RestTemplate resTem;

    @Autowired
    private HistoriaMedicaRepositorio repo;

    public List<HistoriaMedica> listar() {
        return repo.findAll();
    }

    // DTO para mapear respuesta del microservicio de pacientes
    private static class PacienteDTO {
        private Long numero; // el ID que devuelve el microservicio

        public Long getNumero() {
            return numero;
        }

        public void setNumero(Long numero) {
            this.numero = numero;
        }
    }

    

    // Guardar historia médica incluyendo todos los campos
    public HistoriaMedica guardar(HistoriaMedica h) {
        if (h.getPacienteId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El idPaciente es obligatorio");
        }

        // comprobación previa (mejor tener también constraint DB)
        if (repo.existsByPacienteId(h.getPacienteId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El paciente ya tiene una historia médica");
        }

        if (h.getFechaCreacion() == null) {
            h.setFechaCreacion(java.time.LocalDate.now());
        }

        try {
            return repo.save(h);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "El paciente ya tiene una historia médica (conflicto)");
        }
    }

    public HistoriaMedica buscar(Long id) {
        Optional<HistoriaMedica> op = repo.findById(id);
        return op.orElse(null);
    }

    public HistoriaMedica actualizar(Long id, HistoriaMedica h) {
        Optional<HistoriaMedica> op = repo.findById(id);
        if (op.isEmpty())
            return null;

       

        HistoriaMedica existente = op.get();
        existente.setTipoSangre(h.getTipoSangre());
        existente.setAlergias(h.getAlergias());
        existente.setPacienteId(h.getPacienteId());

        // Nuevos campos
        existente.setEnfermedadesCronicas(h.getEnfermedadesCronicas());
        existente.setAntecedentesFamiliares(h.getAntecedentesFamiliares());

        return repo.save(existente);
    }

    public HistoriaMedica buscarPorPacienteId(Long pacienteId) {

        Optional<HistoriaMedica> op = repo.findByPacienteId(pacienteId);
        if (op.isPresent()) {
            return op.get();
        } else {
            throw new RuntimeException("El paciente no tiene historia médica registrada");
        }
    }
    

    // Eliminar historia médica por ID
    public void eliminar(Long idHistoriaMedica) {
        Optional<HistoriaMedica> op = repo.findById(idHistoriaMedica);
        if (op.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La historia médica no existe");
        }

        try {
            repo.deleteById(idHistoriaMedica);
        } catch (DataIntegrityViolationException ex) {
            // Si por algún motivo hay restricciones de integridad
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar la historia médica debido a restricciones de integridad");
        }
    }

}
