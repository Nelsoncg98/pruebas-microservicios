package clinica.pagos.repository;

import clinica.pagos.model.Pago;
import clinica.pagos.model.EstadoPago;
import clinica.pagos.model.TipoPago;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import clinica.pagos.model.TipoPago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    
    Optional<Pago> findByNumeroTransaccion(String numeroTransaccion);
    
    List<Pago> findByBoletaId(Long boletaId);
    
    Optional<Pago> findByNumeroBoleta(String numeroBoleta);
    
    List<Pago> findByPacienteId(Long pacienteId);
    
    List<Pago> findByEstadoPago(EstadoPago estadoPago);
    
    List<Pago> findByTipoPago(TipoPago tipoPago);
    
    List<Pago> findByCajeroId(Long cajeroId);
    
    List<Pago> findByFechaPagoBetween(LocalDateTime inicio, LocalDateTime fin);
    
    boolean existsByBoletaId(Long boletaId);
    
    boolean existsByNumeroTransaccion(String numeroTransaccion);
    
    // Consultas para el cajero
    @Query("SELECT p FROM Pago p WHERE p.cajeroId = :cajeroId AND p.fechaPago BETWEEN :inicio AND :fin")
    List<Pago> findPagosByCajeroYFecha(Long cajeroId, LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT p FROM Pago p WHERE p.estadoPago = :estado AND p.fechaPago BETWEEN :inicio AND :fin")
    List<Pago> findByEstadoAndFechaBetween(EstadoPago estado, LocalDateTime inicio, LocalDateTime fin);
    
    // Estadísticas
    @Query("SELECT COUNT(p) FROM Pago p WHERE p.estadoPago = 'COMPLETADO' AND p.fechaPago BETWEEN :inicio AND :fin")
    Long contarPagosCompletados(LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT SUM(p.montoPagado) FROM Pago p WHERE p.estadoPago = 'COMPLETADO' AND p.fechaPago BETWEEN :inicio AND :fin")
    Double calcularMontoTotalPagado(LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT SUM(p.montoPagado) FROM Pago p WHERE p.cajeroId = :cajeroId AND p.estadoPago = 'COMPLETADO' AND p.fechaPago BETWEEN :inicio AND :fin")
    Double calcularMontoTotalPorCajero(Long cajeroId, LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT p.tipoPago, COUNT(p), SUM(p.montoPagado) FROM Pago p WHERE p.estadoPago = 'COMPLETADO' GROUP BY p.tipoPago")
    List<Object[]> estadisticasPorTipoPago();
}