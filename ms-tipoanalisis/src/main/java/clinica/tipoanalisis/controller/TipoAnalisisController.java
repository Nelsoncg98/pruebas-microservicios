package clinica.tipoanalisis.controller;

import clinica.tipoanalisis.model.TipoAnalisis;
import clinica.tipoanalisis.repository.TipoAnalisisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipoanalisis")
public class TipoAnalisisController {

    @Autowired
    private TipoAnalisisRepository repository;

    @GetMapping("/listar")
    public List<TipoAnalisis> listar() {
        return repository.findAll();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<TipoAnalisis> obj = repository.findById(id);
        if (obj.isPresent()) {
            return ResponseEntity.ok(obj.get());
        }
        return ResponseEntity.notFound().build();
    }
}
