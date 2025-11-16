package clinica.solicitudcita;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Controlador REST para el proceso de solicitud de cita
@RestController
@RequestMapping("/solicitudcita")
public class SolicitudCitaControl {

    @Autowired
    private SolicitudCitaServicio servicio;

    // Endpoint para listar médicos (y luego horarios) disponibles por especialidad
    // GET /solicitudcita/horariosdisponibles?especialidad=Cardiologia
    @GetMapping("/horariosdisponibles")
    public ResponseEntity<Medico[]> horariosDisponibles(@RequestParam String especialidad) {
        Medico[] medicos = servicio.listarHorariosDisponiblesPorEspecialidad(especialidad);
        return new ResponseEntity<>(medicos, HttpStatus.OK);
    }

    // Endpoint para confirmar una cita
    // POST /solicitudcita/confirmar?idPaciente=1&idDoctor=1&horarioId=1&motivo=...&tipoCita=...
    @PostMapping("/confirmar")
    public ResponseEntity<Cita> confirmar(
            @RequestParam Integer idPaciente,
            @RequestParam Integer idDoctor,
            @RequestParam Integer horarioId,
            @RequestParam(required = false) String motivo,
            @RequestParam String tipoCita,
            @RequestParam(required = false) Double costo) {
        Cita cita = servicio.confirmarCita(idPaciente, idDoctor, horarioId, motivo, tipoCita, costo);
        return new ResponseEntity<>(cita, HttpStatus.CREATED);
    }
}
