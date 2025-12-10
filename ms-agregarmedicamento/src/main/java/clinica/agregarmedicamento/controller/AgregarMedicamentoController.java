package clinica.agregarmedicamento.controller;

import clinica.agregarmedicamento.dto.EntradaAgregar;
import clinica.agregarmedicamento.service.AgregarMedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agregarmedicamento")
public class AgregarMedicamentoController {

    @Autowired
    private AgregarMedicamentoService service;

    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody EntradaAgregar entrada) {
        try {
            return ResponseEntity.ok(service.agregar(entrada));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al agregar medicamento: " + e.getMessage());
        }
    }
}
