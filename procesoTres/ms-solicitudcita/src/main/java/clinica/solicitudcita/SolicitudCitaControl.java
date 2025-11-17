package clinica.solicitudcita;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
// Controlador REST para el proceso de solicitud de cita
@RestController
@RequestMapping("/solicitudcita")
public class SolicitudCitaControl {

    @Autowired
    private SolicitudCitaServicio servicio;

    // Endpoint para listar médicos con horarios disponibles por especialidad
    // GET /solicitudcita/horariosdisponibles?especialidad=Cardiologia
    @GetMapping("/horariosdisponibles")
    public ResponseEntity<List<MedicoConHorarios>> horariosDisponibles(@RequestParam String especialidad) {
        List<MedicoConHorarios> medicos = servicio.listarHorariosDisponiblesPorEspecialidad(especialidad);
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

    // Endpoint para obtener todas las citas de un paciente con sus datos básicos
    // GET /solicitudcita/citasPorPaciente?idPaciente=1
    @GetMapping("/citasPorPaciente")
    public ResponseEntity<CitasPorPaciente> citasPorPaciente(@RequestParam Long idPaciente) {
        CitasPorPaciente dto = servicio.obtenerCitasPorPaciente(idPaciente);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // Manejo simple de errores para devolver JSON con el mensaje
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> manejarErrores(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("mensaje", ex.getMessage()));
    }
}
