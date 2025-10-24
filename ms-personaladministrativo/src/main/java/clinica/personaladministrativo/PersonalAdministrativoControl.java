package clinica.personaladministrativo;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/personaladministrativo")
public class PersonalAdministrativoControl {
    private final PersonalAdministrativoServicio servicio;

    public PersonalAdministrativoControl(PersonalAdministrativoServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<PersonalAdministrativo> listar() { return servicio.listar(); }

    @GetMapping("/{id}")
    public PersonalAdministrativo buscar(@PathVariable Long id) { return servicio.buscar(id); }

    @PostMapping
    public PersonalAdministrativo guardar(@RequestBody PersonalAdministrativo p) { return servicio.guardar(p); }

    @PutMapping("/{id}")
    public PersonalAdministrativo actualizar(@PathVariable Long id, @RequestBody PersonalAdministrativo p) { return servicio.actualizar(id, p); }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { servicio.eliminar(id); }
}
