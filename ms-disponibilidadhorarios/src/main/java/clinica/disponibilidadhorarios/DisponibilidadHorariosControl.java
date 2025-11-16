package clinica.disponibilidadhorarios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/disponibilidad")
public class DisponibilidadHorariosControl {

    @Autowired
    private DisponibilidadHorariosServicio serv;

    /**
    * Endpoint compuesto para consultar la disponibilidad de horarios de médicos
    * con filtros por fecha, médico, consultorio y flag disponible.
     */
    @GetMapping("/disponibles")
    public ResponseEntity<List<HorarioMedicoEntrada>> disponibles(
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
        @RequestParam(required = false) Long medicoId,
        @RequestParam(required = false) String consultorio,
        @RequestParam(required = false) Boolean disponible
    ) {
        return ResponseEntity.ok(serv.horariosDisponibles(fecha, medicoId, consultorio, disponible));
    }
}
