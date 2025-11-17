package clinica.pagos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import clinica.pagos.model.EstadoPago;
import clinica.pagos.model.Pago;

@Repository
public interface PagoRepository  extends JpaRepository<Pago, Long>{
List<Pago> findByPacienteId(Long pacienteId);
    
    List<Pago> findByConsultaId(Long consultaId);
    
    List<Pago> findByEstado(EstadoPago estado);
    
    List<Pago> findByFechaPagoBetween(LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT p FROM Pago p WHERE p.pacienteId = :pacienteId AND p.estado = :estado")
    List<Pago> findByPacienteIdAndEstado(Long pacienteId, EstadoPago estado);
    
    @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.estado = 'COMPLETADO' AND p.fechaPago BETWEEN :inicio AND :fin")
    Double getTotalPagosEntreFechas(LocalDateTime inicio, LocalDateTime fin);
}
