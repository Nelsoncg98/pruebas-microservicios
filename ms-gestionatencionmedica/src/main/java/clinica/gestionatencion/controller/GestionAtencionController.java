package clinica.gestionatencion.controller;

import clinica.gestionatencion.dto.EntradaAtencion;
import clinica.gestionatencion.dto.SalidaAtencion;
import clinica.gestionatencion.service.GestionAtencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;



@RestController
@RequestMapping("/gestionatencion")
@CrossOrigin(origins = "http://localhost:5173")
public class GestionAtencionController {

    @Autowired
    private GestionAtencionService service;

    @PostMapping("/registrar")
    public ResponseEntity<SalidaAtencion> registrar(@RequestBody EntradaAtencion entrada) {
        return ResponseEntity.ok(service.registrarAtencion(entrada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalidaAtencion> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarAtencion(id));
    }
}
