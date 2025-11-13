package clinica.historiamedica;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface HistoriaMedicaRepositorio extends JpaRepository<HistoriaMedica, Long> {
      Optional<HistoriaMedica> findByPacienteId(Long pacienteId);
}

