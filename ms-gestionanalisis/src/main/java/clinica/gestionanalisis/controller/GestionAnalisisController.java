package clinica.gestionanalisis.controller;

import clinica.gestionanalisis.dto.EntradaAnalisis;
import clinica.gestionanalisis.service.GestionAnalisisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gestionanalisis")
public class GestionAnalisisController {

    @Autowired
    private GestionAnalisisService service;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@RequestBody EntradaAnalisis entrada) {
        try {
            return ResponseEntity.ok(service.nuevo(entrada));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        return ResponseEntity.ok(service.ver(id));
    }

    @PostMapping("/finalizar/{id}")
    public ResponseEntity<?> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(service.finalizar(id));
    }
}
