package clinica.paciente;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PacienteRepositorio extends JpaRepository<Paciente, Long> {
	Optional<Paciente> findByDni(String dni);
}
