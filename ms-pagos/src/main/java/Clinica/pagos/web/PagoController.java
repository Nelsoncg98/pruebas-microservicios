package Clinica.pagos.web;

import Clinica.pagos.model.Pago;
import Clinica.pagos.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pago")
public class PagoController {

    @Autowired
    private PagoService service;

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody Pago pago){
        try{
            Pago p = service.crear(pago);
            return ResponseEntity.ok(present(p));
        } catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id){
        return service.buscar(id)
                .map(p -> ResponseEntity.ok(present(p)))
                .orElseGet(() -> ResponseEntity.status(404).body(Map.of("message","Pago no encontrado")));
    }

    // Forma la respuesta con las claves solicitadas por el usuario
    private Map<String,Object> present(Pago p){
        Map<String,Object> m = new HashMap<>();
        m.put("idpago", p.getId());
        m.put("idcita", p.getIdCita());
        m.put("montopagar", p.getMontopagar());
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        m.put("fecha", p.getFecha() != null ? p.getFecha().format(fmt) : null);
        m.put("tipo de pago", p.getTipoPago());
        m.put("estado depago", p.getEstadoPago());
        return m;
    }
}
