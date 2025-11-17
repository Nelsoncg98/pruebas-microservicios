package clinica.pagos.controller;

import clinica.pagos.dto.PagoDto;
import clinica.pagos.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {
    
    private final PagoService pagoService;
    
    @PostMapping
    public ResponseEntity<PagoDto> crearPago(@RequestBody PagoDto pagoDto) {
        try {
            PagoDto nuevoPago = pagoService.crearPago(pagoDto);
            return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PagoDto> obtenerPagoPorId(@PathVariable Long id) {
        try {
            PagoDto pago = pagoService.obtenerPagoPorId(id);
            return ResponseEntity.ok(pago);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<PagoDto>> obtenerPagosPorPaciente(@PathVariable Long pacienteId) {
        try {
            List<PagoDto> pagos = pagoService.obtenerPagosPorPaciente(pacienteId);
            return ResponseEntity.ok(pagos);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<PagoDto>> obtenerTodosPagos() {
        List<PagoDto> pagos = pagoService.obtenerTodosPagos();
        return ResponseEntity.ok(pagos);
    }
    
    @PutMapping("/{id}/estado")
    public ResponseEntity<PagoDto> actualizarEstadoPago(
            @PathVariable Long id, 
            @RequestParam String estado) {
        try {
            PagoDto pagoActualizado = pagoService.actualizarEstadoPago(id, estado);
            return ResponseEntity.ok(pagoActualizado);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}