package clinica.pagos.service;

import clinica.pagos.client.CitaClient;
import clinica.pagos.client.PacienteClient;
import clinica.pagos.dto.CitaDto;
import clinica.pagos.dto.PacienteDto;
import clinica.pagos.model.*;
import clinica.pagos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio de Pago - Pasos 4 y 5 del flujo: "Cajero procesa pago" y
 * "Confirmación de pago"
 * Aplica todas las validaciones de reglas de negocio
 */
@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private CitaClient citaClient;

    @Autowired
    private PacienteClient pacienteClient;

    /**
     * Crear un nuevo pago
     */
    @Transactional
    public Pago crearPago(Pago pago) {
        // Generar números únicos
        pago.setNumeroTransaccion(generarNumeroTransaccion());
        pago.setNumeroBoleta(generarNumeroBoleta());
        pago.setFechaPago(LocalDateTime.now());
        pago.setEstadoPago(EstadoPago.PENDIENTE);
        
        return pagoRepository.save(pago);
    }

    /**
     * Confirmar pago
     */
    @Transactional
    public Pago confirmarPago(Long id, String numeroComprobante, String detalleComprobante) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        if (pago.getEstadoPago() != EstadoPago.PENDIENTE) {
            throw new RuntimeException("Solo se pueden confirmar pagos en estado PENDIENTE");
        }

        pago.setEstadoPago(EstadoPago.COMPLETADO);
        pago.setNumeroComprobante(numeroComprobante);
        pago.setComprobanteEmitido(true);
        pago.setObservaciones(detalleComprobante != null ? detalleComprobante : "Pago confirmado");
        
        return pagoRepository.save(pago);
    }

    /**
     * Rechazar pago
     */
    @Transactional
    public Pago rechazarPago(Long id, String motivo) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        if (pago.getEstadoPago() != EstadoPago.PENDIENTE) {
            throw new RuntimeException("Solo se pueden rechazar pagos en estado PENDIENTE");
        }

        pago.setEstadoPago(EstadoPago.RECHAZADO);
        pago.setObservaciones("Rechazado: " + motivo);
        
        return pagoRepository.save(pago);
    }

    /**
     * Anular pago
     */
    @Transactional
    public Pago anularPago(Long id, String motivo) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        if (pago.getEstadoPago() != EstadoPago.COMPLETADO) {
            throw new RuntimeException("Solo se pueden anular pagos COMPLETADOS");
        }

        pago.setEstadoPago(EstadoPago.ANULADO);
        pago.setObservaciones("Anulado: " + motivo);
        
        return pagoRepository.save(pago);
    }

    /**
     * Listar todos los pagos
     */
    public List<Pago> listarTodosPagos() {
        return pagoRepository.findAll();
    }

    /**
     * Buscar pago por ID
     */
    public Optional<Pago> buscarPagoPorId(Long id) {
        return pagoRepository.findById(id);
    }

    /**
     * Buscar pago por número de transacción
     */
    public Optional<Pago> buscarPagoPorNumeroTransaccion(String numeroTransaccion) {
        return pagoRepository.findByNumeroTransaccion(numeroTransaccion);
    }

    /**
     * Buscar pago por número de boleta
     */
    public Optional<Pago> buscarPagoPorNumeroBoleta(String numeroBoleta) {
        return pagoRepository.findByNumeroBoleta(numeroBoleta);
    }

    /**
     * Buscar pagos por paciente
     */
    public List<Pago> buscarPagosPorPaciente(Long pacienteId) {
        return pagoRepository.findByPacienteId(pacienteId);
    }

    /**
     * Buscar pagos por estado
     */
    public List<Pago> buscarPagosPorEstado(EstadoPago estado) {
        return pagoRepository.findByEstadoPago(estado);
    }

    /**
     * Buscar pagos por tipo de pago
     */
    public List<Pago> buscarPagosPorTipo(TipoPago tipo) {
        return pagoRepository.findByTipoPago(tipo);
    }

    /**
     * Buscar pagos por cajero
     */
    public List<Pago> buscarPagosPorCajero(Long cajeroId) {
        return pagoRepository.findByCajeroId(cajeroId);
    }

    /**
     * Obtener pago completo (con cita y paciente)
     */
    public Map<String, Object> obtenerPagoCompleto(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("pago", pago);

        // Obtener cita usando boletaId
        try {
            CitaDto cita = citaClient.obtenerCitaPorId(pago.getBoletaId());
            resultado.put("cita", cita);
        } catch (Exception e) {
            resultado.put("cita", null);
        }

        // Obtener paciente
        try {
            PacienteDto paciente = pacienteClient.obtenerPacientePorId(pago.getPacienteId());
            resultado.put("paciente", paciente);
        } catch (Exception e) {
            resultado.put("paciente", null);
        }

        return resultado;
    }

    /**
     * Obtener estadísticas del cajero
     */
    public Map<String, Object> obtenerEstadisticasCajero(Long cajeroId, LocalDateTime inicio, LocalDateTime fin) {
        List<Pago> pagos = pagoRepository.findByCajeroId(cajeroId).stream()
                .filter(p -> p.getFechaPago().isAfter(inicio) && p.getFechaPago().isBefore(fin))
                .collect(Collectors.toList());

        Map<String, Object> estadisticas = new HashMap<>();
        estadisticas.put("cajeroId", cajeroId);
        estadisticas.put("totalPagos", pagos.size());
        estadisticas.put("totalConfirmados", pagos.stream().filter(p -> p.getEstadoPago() == EstadoPago.COMPLETADO).count());
        estadisticas.put("totalRechazados", pagos.stream().filter(p -> p.getEstadoPago() == EstadoPago.RECHAZADO).count());
        estadisticas.put("totalAnulados", pagos.stream().filter(p -> p.getEstadoPago() == EstadoPago.ANULADO).count());
        estadisticas.put("montoTotal", pagos.stream()
                .filter(p -> p.getEstadoPago() == EstadoPago.COMPLETADO)
                .mapToDouble(Pago::getMontoPagado)
                .sum());

        return estadisticas;
    }

    /**
     * Generar número de transacción único
     */
    private String generarNumeroTransaccion() {
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = pagoRepository.count() + 1;
        return String.format("TXN-%s-%04d", fecha, count);
    }

    /**
     * Generar número de boleta único
     */
    private String generarNumeroBoleta() {
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = pagoRepository.count() + 1;
        return String.format("BOL-%s-%04d", fecha, count);
    }
}