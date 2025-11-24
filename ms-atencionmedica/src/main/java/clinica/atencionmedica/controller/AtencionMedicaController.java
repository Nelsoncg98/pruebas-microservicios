package clinica.atencionmedica.controller;

import clinica.atencionmedica.model.AtencionMedica;
import clinica.atencionmedica.service.AtencionMedicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173") // habilitar CORS para el frontend en desarrollo
@RestController
@RequestMapping("/atencion")
public class AtencionMedicaController {

    @Autowired
    private AtencionMedicaService service;

    @PostMapping("/registrar")
    public ResponseEntity<AtencionMedica> registrar(@RequestBody AtencionMedica atencion) {
        return ResponseEntity.ok(service.guardar(atencion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtencionMedica> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cita/{idCita}")
    public ResponseEntity<List<AtencionMedica>> buscarPorCita(@PathVariable Long idCita) {
        return ResponseEntity.ok(service.buscarPorCita(idCita));
    }

    @GetMapping
    public ResponseEntity<List<AtencionMedica>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }
}
