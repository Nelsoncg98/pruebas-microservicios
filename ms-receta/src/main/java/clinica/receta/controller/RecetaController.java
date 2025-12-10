package clinica.receta.controller;

import clinica.receta.model.Receta;
import clinica.receta.repository.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/receta")
public class RecetaController {

    @Autowired
    private RecetaRepository repository;

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> body) {
        try {
            Long idAtencion = Long.valueOf(body.get("idAtencion").toString());
            Long idMedico = Long.valueOf(body.get("idMedico").toString());
            
            Receta receta = new Receta();
            receta.setIdAtencion(idAtencion);
            receta.setIdMedico(idMedico);
            receta.setFecha(LocalDateTime.now());
            receta.setEstado("BORRADOR");
            
            return ResponseEntity.ok(repository.save(receta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear receta: " + e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<Receta> receta = repository.findById(id);
        if (receta.isPresent()) {
            return ResponseEntity.ok(receta.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/actualizarEstado/{id}")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Optional<Receta> opt = repository.findById(id);
        if (opt.isPresent()) {
            Receta receta = opt.get();
            String nuevoEstado = body.get("estado");
            if (nuevoEstado != null) {
                receta.setEstado(nuevoEstado);
                return ResponseEntity.ok(repository.save(receta));
            }
            return ResponseEntity.badRequest().body("Estado no proporcionado");
        }
        return ResponseEntity.notFound().build();
    }
}
