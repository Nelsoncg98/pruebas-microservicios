package clinica.agregartipo.controller;

import clinica.agregartipo.dto.EntradaAgregar;
import clinica.agregartipo.service.AgregarTipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173") // habilitar CORS para el frontend en desarrollo
@RestController
@RequestMapping("/agregartipo")
public class AgregarTipoController {

    @Autowired
    private AgregarTipoService service;

    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody EntradaAgregar entrada) {
        try {
            return ResponseEntity.ok(service.agregar(entrada));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
