package clinica.historiamedica;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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

    private boolean validarPaciente(Long idPaciente) {
        if (idPaciente == null)
            return false;

        String url = "http://localhost:8092/paciente/buscar/" + idPaciente;
        try {
            PacienteDTO paciente = resTem.getForObject(url, PacienteDTO.class);
            return paciente != null && paciente.getNumero() != null && paciente.getNumero().equals(idPaciente);
        } catch (RestClientException e) {
            return false;
        }
    }

    // Guardar historia médica incluyendo todos los campos
    public HistoriaMedica guardar(HistoriaMedica h) {
        if (h.getPacienteId() == null) {
            throw new RuntimeException("El idPaciente es obligatorio");
        }

        // if (!validarPaciente(h.getPacienteId())) {
        // throw new RuntimeException("El paciente no existe en el microservicio de
        // pacientes");
        // }

        if (h.getFechaCreacion() == null) {
            h.setFechaCreacion(java.time.LocalDate.now());
        }

        return repo.save(h);
    }

    public HistoriaMedica buscar(Long id) {
        Optional<HistoriaMedica> op = repo.findById(id);
        return op.orElse(null);
    }

    public HistoriaMedica actualizar(Long id, HistoriaMedica h) {
        Optional<HistoriaMedica> op = repo.findById(id);
        if (op.isEmpty())
            return null;

        if (!validarPaciente(h.getPacienteId())) {
            throw new RuntimeException("El paciente no existe en el microservicio de pacientes");
        }

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
        // if (!validarPaciente(pacienteId)) {
        // throw new RuntimeException("El paciente no existe en el microservicio de
        // pacientes");
        // }

        Optional<HistoriaMedica> op = repo.findByPacienteId(pacienteId);
        if (op.isPresent()) {
            return op.get();
        } else {
            throw new RuntimeException("El paciente no tiene historia médica registrada");
        }
    }
}
