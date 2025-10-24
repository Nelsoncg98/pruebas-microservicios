package clinica.horariomedico;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioMedicoRepositorio extends JpaRepository<HorarioMedico, Long> {
    List<HorarioMedico> findByDisponibleTrue();
    List<HorarioMedico> findByFechaAndDisponibleTrue(LocalDate fecha);
    List<HorarioMedico> findByFechaAndMedicoIdAndDisponibleTrue(LocalDate fecha, Long medicoId);
    List<HorarioMedico> findByMedicoIdAndDisponibleTrue(Long medicoId);
}
