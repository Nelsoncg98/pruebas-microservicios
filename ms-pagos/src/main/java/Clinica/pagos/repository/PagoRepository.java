package clinica.pagos.repository;

import clinica.pagos.model.Pago;
import clinica.pagos.model.EstadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    
    List<Pago> findByPacienteId(Long pacienteId);
    
    List<Pago> findByEstado(EstadoPago estado);
    
    Optional<Pago> findByCitaId(Long citaId);
    
    List<Pago> findByPacienteIdAndEstado(Long pacienteId, EstadoPago estado);
}