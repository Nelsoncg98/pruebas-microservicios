package clinica.gestionboleta.controller;

import clinica.gestionboleta.dto.EntradaBoleta;
import clinica.gestionboleta.dto.SalidaBoleta;
import clinica.gestionboleta.service.GestionBoletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gestionboleta")
@CrossOrigin(origins = "*")
public class GestionBoletaController {

    @Autowired
    private GestionBoletaService service;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@RequestBody EntradaBoleta entrada) {
        try {
            return ResponseEntity.ok(service.nuevo(entrada));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear boleta: " + e.getMessage());
        }
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.ver(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al ver boleta: " + e.getMessage());
        }
    }
    
    @PostMapping("/confirmar/{id}")
    public ResponseEntity<?> confirmar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.confirmar(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al confirmar boleta: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.ok("Boleta eliminada");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar boleta: " + e.getMessage());
        }
    }
}
