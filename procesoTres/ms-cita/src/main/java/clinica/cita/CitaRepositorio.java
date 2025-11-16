package clinica.cita;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CitaRepositorio extends JpaRepository<Cita, Long> {
	List<Cita> findByPacienteId(Long pacienteId);
}
