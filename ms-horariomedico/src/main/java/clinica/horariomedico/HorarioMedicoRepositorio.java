package clinica.horariomedico;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioMedicoRepositorio extends JpaRepository<HorarioMedico, Long> {
    
    List<HorarioMedico> findByFecha(LocalDate fecha);
    List<HorarioMedico> findByFechaAndMedicoId(LocalDate fecha, Long medicoId);
    List<HorarioMedico> findByMedicoId(Long medicoId);
    
    // Consultas que incluyen el flag disponible
    List<HorarioMedico> findByFechaAndDisponible(LocalDate fecha, Boolean disponible);
    List<HorarioMedico> findByFechaAndMedicoIdAndDisponible(LocalDate fecha, Long medicoId, Boolean disponible);
    List<HorarioMedico> findByMedicoIdAndDisponible(Long medicoId, Boolean disponible);
    List<HorarioMedico> findByDisponible(Boolean disponible);
}
