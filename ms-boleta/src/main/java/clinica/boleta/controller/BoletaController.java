package clinica.boleta.controller;

import clinica.boleta.model.Boleta;
import clinica.boleta.service.BoletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/boleta")
@CrossOrigin(origins = "*") 
public class BoletaController {

    @Autowired
    private BoletaService service;

    @PostMapping("/crear")
    public ResponseEntity<Boleta> crear(@RequestBody Boleta boleta) {
        return ResponseEntity.ok(service.guardar(boleta));
    }

    @GetMapping("/listar")
    public List<Boleta> listar() {
        return service.listar();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Boleta> buscar(@PathVariable Long id) {
        Optional<Boleta> boleta = service.buscar(id);
        if (boleta.isPresent()) {
            return ResponseEntity.ok(boleta.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/actualizar")
    public ResponseEntity<Boleta> actualizar(@RequestBody Boleta boleta) {
         return ResponseEntity.ok(service.guardar(boleta));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Eliminado");
    }
}
