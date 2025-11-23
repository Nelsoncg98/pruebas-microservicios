package clinica.pagos.controller;

import clinica.pagos.model.Pago;
import clinica.pagos.model.EstadoPago;
import clinica.pagos.model.TipoPago;
import clinica.pagos.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestión de Pagos
 * Pasos 4 y 5 del flujo: "Cajero procesa pago" y "Confirmación de pago"
 */
@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    /**
     * Crear nuevo pago
     * POST /api/pagos/crear
     */
    @PostMapping("/crear")
    public ResponseEntity<?> crearPago(@RequestBody Pago pago) {
        try {
            Pago nuevoPago = pagoService.crearPago(pago);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Confirmar pago
     * PUT /api/pagos/{id}/confirmar
     */
    @PutMapping("/{id}/confirmar")

    public ResponseEntity<?> confirmarPago(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        try {
            String numeroComprobante = request.get("numeroComprobante");
            String tipoComprobante = request.get("tipoComprobante");

            Pago pagoConfirmado = pagoService.confirmarPago(id, numeroComprobante, tipoComprobante);
            return ResponseEntity.ok(pagoConfirmado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Rechazar pago
     * PUT /api/pagos/{id}/rechazar
     */
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazarPago(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        try {
            String motivo = request.get("motivo");
            Pago pagoRechazado = pagoService.rechazarPago(id, motivo);
            return ResponseEntity.ok(pagoRechazado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Anular pago
     * PUT /api/pagos/{id}/anular
     */
    @PutMapping("/{id}/anular")
    public ResponseEntity<?> anularPago(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        try {
            String motivo = request.get("motivo");
            Pago pagoAnulado = pagoService.anularPago(id, motivo);
            return ResponseEntity.ok(pagoAnulado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Listar todos los pagos
     * GET /api/pagos
     */
    @GetMapping
    public ResponseEntity<List<Pago>> listarPagos() {
        List<Pago> pagos = pagoService.listarTodosPagos();
        return ResponseEntity.ok(pagos);
    }

    /**
     * Buscar pago por ID
     * GET /api/pagos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPagoPorId(@PathVariable Long id) {
        return pagoService.buscarPagoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Buscar pago por número de transacción
     * GET /api/pagos/transaccion/{numeroTransaccion}
     */
    @GetMapping("/transaccion/{numeroTransaccion}")
    public ResponseEntity<?> buscarPagoPorTransaccion(@PathVariable String numeroTransaccion) {
        return pagoService.buscarPagoPorNumeroTransaccion(numeroTransaccion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Buscar pagos por paciente
     * GET /api/pagos/paciente/{pacienteId}
     */
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Pago>> buscarPagosPorPaciente(@PathVariable Long pacienteId) {
        List<Pago> pagos = pagoService.buscarPagosPorPaciente(pacienteId);
        return ResponseEntity.ok(pagos);
    }

    /**
     * Buscar pagos por estado
     * GET /api/pagos/estado/{estado}
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pago>> buscarPagosPorEstado(@PathVariable EstadoPago estado) {
        List<Pago> pagos = pagoService.buscarPagosPorEstado(estado);
        return ResponseEntity.ok(pagos);
    }

    /**
     * Buscar pagos por tipo de pago
     * GET /api/pagos/tipo/{tipo}
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Pago>> buscarPagosPorTipo(@PathVariable TipoPago tipo) {
        List<Pago> pagos = pagoService.buscarPagosPorTipo(tipo);
        return ResponseEntity.ok(pagos);
    }

    /**
     * Buscar pagos por cajero
     * GET /api/pagos/cajero/{cajeroId}
     */
    @GetMapping("/cajero/{cajeroId}")
    public ResponseEntity<List<Pago>> buscarPagosPorCajero(@PathVariable Long cajeroId) {
        List<Pago> pagos = pagoService.buscarPagosPorCajero(cajeroId);
        return ResponseEntity.ok(pagos);
    }

    /**
     * Obtener pago completo (incluye cita y paciente)
     * GET /api/pagos/{id}/completo
     */
    @GetMapping("/{id}/completo")
    public ResponseEntity<?> obtenerPagoCompleto(@PathVariable Long id) {
        try {
            Map<String, Object> pagoCompleto = pagoService.obtenerPagoCompleto(id);
            return ResponseEntity.ok(pagoCompleto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Estadísticas del cajero
     * GET /api/pagos/estadisticas/cajero/{cajeroId}
     */
    @GetMapping("/estadisticas/cajero/{cajeroId}")
    public ResponseEntity<?> obtenerEstadisticasCajero(
            @PathVariable Long cajeroId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        try {
            Map<String, Object> estadisticas = pagoService.obtenerEstadisticasCajero(cajeroId, inicio, fin);
            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
