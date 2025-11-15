package clinica.expedienteclinico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import clinica.expedienteclinico.dto.ExpedienteClinicoDTO;
import clinica.expedienteclinico.ExpedienteClinicoServicio;

@RestController
@RequestMapping("/expediente")
@CrossOrigin(origins = "http://localhost:5173")
public class ExpedienteClinicoControl {

    @Autowired
    private ExpedienteClinicoServicio servicio;

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<ExpedienteClinicoDTO> obtenerPorPaciente(@PathVariable Long pacienteId) {
        ExpedienteClinicoDTO dto = servicio.obtenerExpediente(pacienteId);
        // devolvemos 200 aunque algunos subcampos sean null/empty para que el frontend
        // maneje la vista
        return ResponseEntity.ok(dto);
    }
}
