package clinica.cita;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitaServicio {

    @Autowired
    private CitaRepositorio repo;

    public List<Cita> listar() {
        return repo.findAll();
    }

    public Cita buscar(Long id) {
        return repo.findById(id).orElse(null);
    }

    

    // Servicio normalizado de entidad: asume que la orquestación
    // (paciente, médico, horario, reservas) ya fue hecha por ms-solicitudcita.
    public Cita crear(Cita cita) {
        // Validaciones básicas de campos mínimos que debe recibir la entidad
        if (cita.getPacienteId() == null) {
            throw new IllegalArgumentException("Se requiere pacienteId");
        }
        if (cita.getIdDoctor() == null || cita.getIdDoctor().isBlank()) {
            throw new IllegalArgumentException("Se requiere idDoctor");
        }
        if (cita.getHorarioId() == null) {
            throw new IllegalArgumentException("Se requiere horarioId");
        }
        if (cita.getMotivo() == null || cita.getMotivo().isBlank()) {
            throw new IllegalArgumentException("Se requiere motivo");
        }
        if (cita.getTipoCita() == null || cita.getTipoCita().isBlank()) {
            throw new IllegalArgumentException("Se requiere tipoCita");
        }

        if (cita.getTipoCita() != null && cita.getTipoCita().length() > 40) {
            throw new IllegalArgumentException("tipoCita demasiado largo");
        }

        // Si no viene fecha, usamos el momento actual
        if (cita.getFecha() == null) {
            cita.setFecha(LocalDateTime.now());
        }

        // Estado por defecto si no viene informado
        if (cita.getEstado() == null || cita.getEstado().isBlank()) {
            cita.setEstado("PENDIENTE");
        }

        return repo.save(cita);
    }

    public Cita cancelar(Long id) {
        Optional<Cita> op = repo.findById(id);
        if (op.isEmpty()) {
            return null;
        }
        Cita c = op.get();
        c.setEstado("CANCELADA");
        return repo.save(c);
    }
}
