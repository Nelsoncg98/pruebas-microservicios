package clinica.medico;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepositorio extends JpaRepository<Medico, Long> {
	// Buscar médicos por especialidad (case-insensitive)
	List<Medico> findByEspecialidadIgnoreCase(String especialidad);
}
