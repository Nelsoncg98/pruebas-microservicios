package clinica.cajero.controller;

import clinica.cajero.model.Cajero;
import clinica.cajero.service.CajeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cajero")
@CrossOrigin(origins = "*") // Open for orchestrator
public class CajeroController {
    
    @Autowired
    private CajeroService service;

    @PostMapping("/crear")
    public ResponseEntity<Cajero> crear(@RequestBody Cajero cajero) {
        return ResponseEntity.ok(service.guardar(cajero));
    }
    
    @GetMapping("/listar")
    public List<Cajero> listar() {
        return service.listar();
    }
    
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Cajero> buscar(@PathVariable Long id) {
        Optional<Cajero> cajero = service.buscar(id);
        if (cajero.isPresent()) {
            return ResponseEntity.ok(cajero.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Eliminado");
    }
}
