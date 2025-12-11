package clinica.detalleanalisis.controller;

import clinica.detalleanalisis.model.DetalleAnalisis;
import clinica.detalleanalisis.repository.DetalleAnalisisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173") // habilitar CORS para el frontend en desarrollo
@RestController
@RequestMapping("/detalleanalisis")
public class DetalleAnalisisController {

    @Autowired
    private DetalleAnalisisRepository repository;

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody Map<String, Object> body) {
        try {
            Long idAnalisis = Long.valueOf(body.get("idAnalisis").toString());
            Long idTipo = Long.valueOf(body.get("idTipo").toString());
            
            // Snapshot extraction
            String nombreTipo = (String) body.get("nombreTipo");
            String descripcionTipo = (String) body.get("descripcionTipo");
            Double costoTipo = body.get("costoTipo") != null ? Double.valueOf(body.get("costoTipo").toString()) : null;
            String laboratorioTipo = (String) body.get("laboratorioTipo");
            
            String indicaciones = (String) body.get("indicaciones");
            
            DetalleAnalisis detalle = new DetalleAnalisis();
            detalle.setIdAnalisis(idAnalisis);
            detalle.setIdTipo(idTipo);
            
            // Snapshot setting
            detalle.setNombreTipo(nombreTipo);
            detalle.setDescripcionTipo(descripcionTipo);
            detalle.setCostoTipo(costoTipo);
            detalle.setLaboratorioTipo(laboratorioTipo);
            
            detalle.setIndicaciones(indicaciones);
            
            return ResponseEntity.ok(repository.save(detalle));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar detalle: " + e.getMessage());
        }
    }

    @GetMapping("/listarPorAnalisis/{id}")
    public List<DetalleAnalisis> listarPorAnalisis(@PathVariable Long id) {
        return repository.findByIdAnalisis(id);
    }
    @GetMapping("/listar")
    public java.util.List<DetalleAnalisis> listar() {
        return repository.findAll();
    }
}
