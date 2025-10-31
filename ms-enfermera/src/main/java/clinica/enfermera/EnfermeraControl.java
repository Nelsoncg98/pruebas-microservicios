package clinica.enfermera;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173") // habilitar CORS para el frontend en desarrollo
@RestController
@RequestMapping("/enfermera")
public class EnfermeraControl {
    @Autowired
    private EnfermeraServicio serv;

    @PostMapping("/guardar")
    public Enfermera guardar(@RequestBody Enfermera e) {
        return serv.guardar(e);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        serv.eliminar(id);
    }

    @GetMapping("/listar")
    public List<Enfermera> listar() {
        return serv.listar();
    }

    @GetMapping("/buscar/{id}")
    public Enfermera buscar(@PathVariable Long id) {
        return serv.buscar(id);
    }

    @DeleteMapping("/limpiar")
    public void limpiar() {
        serv.limpiar();
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Enfermera> actualizar(@PathVariable Long id, @RequestBody Enfermera e) {
        Enfermera updated = serv.actualizar(id, e);
        if (updated == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(updated);
    }
}
