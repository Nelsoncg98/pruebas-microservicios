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
     * PASO 4: Cajero procesa pago
     * POST /api/pagos/procesar
     */
    @PostMapping("/procesar")
    public ResponseEntity<?> procesarPago(@RequestBody Pago pago) {
        try {
            Pago pagoCreado = pagoService.procesarPago(pago);
            return ResponseEntity.status(HttpStatus.CREATED).body(pagoCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    
    @PostMapping("/{id}/confirmar")
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
    
    
    @PostMapping("/{id}/rechazar")
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
    
    
    @PostMapping("/{id}/anular")
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
    
    
    @GetMapping
    public ResponseEntity<List<Pago>> listarPagos() {
        List<Pago> pagos = pagoService.listarTodosPagos();
        return ResponseEntity.ok(pagos);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPagoPorId(@PathVariable Long id) {
        return pagoService.buscarPagoPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
   
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Pago>> buscarPagosPorPaciente(@PathVariable Long pacienteId) {
        List<Pago> pagos = pagoService.buscarPagosPorPaciente(pacienteId);
        return ResponseEntity.ok(pagos);
    }
    
    
    
   
    
    /**
     * Obtener pago completo (incluye boleta y paciente)
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
    
   
    
}

