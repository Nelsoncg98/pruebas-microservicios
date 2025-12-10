package clinica.analisis.controller;

import clinica.analisis.model.Analisis;
import clinica.analisis.repository.AnalisisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/analisis")
public class AnalisisController {

    @Autowired
    private AnalisisRepository repository;

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody Analisis analisis) {
        return ResponseEntity.ok(repository.save(analisis));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<Analisis> obj = repository.findById(id);
        if (obj.isPresent()) {
            return ResponseEntity.ok(obj.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/actualizarEstado/{id}")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody String nuevoEstado) {
        Optional<Analisis> obj = repository.findById(id);
        if (obj.isPresent()) {
            Analisis a = obj.get();
            a.setEstado(nuevoEstado);
            return ResponseEntity.ok(repository.save(a));
        }
        return ResponseEntity.notFound().build();
    }
}
