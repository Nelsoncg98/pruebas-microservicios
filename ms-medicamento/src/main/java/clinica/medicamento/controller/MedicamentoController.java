package clinica.medicamento.controller;

import clinica.medicamento.model.Medicamento;
import clinica.medicamento.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicamento")
public class MedicamentoController {

    @Autowired
    private MedicamentoRepository repository;

    @GetMapping("/listar")
    public List<Medicamento> listar() {
        return repository.findAll();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<Medicamento> obj = repository.findById(id);
        if (obj.isPresent()) {
            return ResponseEntity.ok(obj.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Medicamento medicamento) {
        return ResponseEntity.ok(repository.save(medicamento));
    }
}
