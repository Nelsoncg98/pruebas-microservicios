package clinica.gestionreceta.controller;

import clinica.gestionreceta.dto.EntradaReceta;
import clinica.gestionreceta.service.GestionRecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173") // habilitar CORS para el frontend en desarrollo
@RestController
@RequestMapping("/gestionreceta")
public class GestionRecetaController {

    @Autowired
    private GestionRecetaService service;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@RequestBody EntradaReceta entrada) {
        try {
            return ResponseEntity.ok(service.nuevo(entrada));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear receta: " + e.getMessage());
        }
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.ver(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al buscar receta: " + e.getMessage());
        }
    }

    @PostMapping("/finalizar/{id}")
    public ResponseEntity<?> finalizar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.finalizar(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al finalizar receta: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminarDetalle/{id}")
    public ResponseEntity<?> eliminarDetalle(@PathVariable Long id) {
        try {
            service.eliminarDetalle(id);
            return ResponseEntity.ok("Detalle eliminado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar detalle: " + e.getMessage());
        }
    }
}
