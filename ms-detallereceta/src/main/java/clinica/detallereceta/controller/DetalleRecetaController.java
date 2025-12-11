package clinica.detallereceta.controller;

import clinica.detallereceta.model.DetalleReceta;
import clinica.detallereceta.repository.DetalleRecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173") // habilitar CORS para el frontend en desarrollo
@RestController
@RequestMapping("/detallereceta")
public class DetalleRecetaController {

    @Autowired
    private DetalleRecetaRepository repository;

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody Map<String, Object> body) {
        try {
            Long idReceta = Long.valueOf(body.get("idReceta").toString());
            Long idMedicamento = Long.valueOf(body.get("idMedicamento").toString());
            // Snapshot extraction with null checks
            String nombreMedicamento = (String) body.get("nombreMedicamento");
            String laboratorio = (String) body.get("laboratorio");
            Double precio = body.get("precio") != null ? Double.valueOf(body.get("precio").toString()) : null;
            
            Integer cantidad = Integer.valueOf(body.get("cantidad").toString());
            String indicaciones = (String) body.get("indicaciones");
            
            DetalleReceta detalle = new DetalleReceta();
            detalle.setIdReceta(idReceta);
            detalle.setIdMedicamento(idMedicamento);
            // Snapshot setting
            detalle.setNombreMedicamento(nombreMedicamento);
            detalle.setLaboratorio(laboratorio);
            detalle.setPrecio(precio);
            
            detalle.setCantidad(cantidad);
            detalle.setIndicaciones(indicaciones);
            
            return ResponseEntity.ok(repository.save(detalle));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar detalle: " + e.getMessage());
        }
    }

    @GetMapping("/listarPorReceta/{idReceta}")
    public List<DetalleReceta> listarPorReceta(@PathVariable Long idReceta) {
        return repository.findByIdReceta(idReceta);
    }
    @GetMapping("/listar")
    public java.util.List<DetalleReceta> listar() {
        return repository.findAll();
    }
}
