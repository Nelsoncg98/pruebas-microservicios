package clinica.nuevaatencion.controller;

import clinica.nuevaatencion.dto.EntradaNuevaAtencion;
import clinica.nuevaatencion.dto.SalidaNuevaAtencion;
import clinica.nuevaatencion.service.NuevaAtencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nuevaatencion")
@CrossOrigin(origins = "http://localhost:5173")
public class NuevaAtencionController {

    @Autowired
    private NuevaAtencionService service;

    @PostMapping("/nuevo")
    public ResponseEntity<SalidaNuevaAtencion> nuevo(@RequestBody EntradaNuevaAtencion entrada) {
        return ResponseEntity.ok(service.prepararAtencion(entrada));
    }
}
