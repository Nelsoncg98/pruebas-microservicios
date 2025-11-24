package clinica.atencionmedica.repository;

import clinica.atencionmedica.model.AtencionMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtencionMedicaRepository extends JpaRepository<AtencionMedica, Long> {
    List<AtencionMedica> findByIdCita(Long idCita);
    List<AtencionMedica> findByIdPaciente(Long idPaciente);
}
