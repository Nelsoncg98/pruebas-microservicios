package clinica.solicitudcita;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

// Servicio compuesto para el proceso de solicitud de cita
@Service
public class SolicitudCitaServicio {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Lista médicos disponibles por especialidad, delegando en ms-medico.
     * Ejemplo de endpoint remoto: http://localhost:8091/medico/porEspecialidad?especialidad=Cardiologia
     */
    public Medico[] listarHorariosDisponiblesPorEspecialidad(String especialidad) {
        String url = "http://ms-medico/medico/porEspecialidad?especialidad=" + especialidad;
        return restTemplate.getForObject(url, Medico[].class);
    }

    /**
     * Confirma una cita médica orquestando la creación de la cita en ms-cita.
     * Si viene un costo explícito, se respeta; si no, se calcula en función del tipoCita.
     */
    public Cita confirmarCita(Integer idPaciente,
                              Integer idDoctor,
                              Integer horarioId,
                              String motivo,
                              String tipoCita,
                              Double costoOpcional) {

        if (idPaciente == null || idDoctor == null || horarioId == null) {
            throw new IllegalArgumentException("idPaciente, idDoctor y horarioId son obligatorios");
        }

        Cita nueva = new Cita();
        nueva.setPacienteId(idPaciente.longValue());
        nueva.setIdDoctor(String.valueOf(idDoctor));
        nueva.setHorarioId(horarioId.longValue());
        nueva.setMotivo(motivo != null ? motivo : "Solicitud de cita" );
        nueva.setTipoCita(tipoCita != null ? tipoCita : "CONSULTA");
        nueva.setFecha(LocalDateTime.now());

        // Si el costo viene informado, se usa tal cual
        if (costoOpcional != null) {
            nueva.setCosto(costoOpcional);
        } else {
            // Caso contrario, se calcula en función del tipo de cita
            nueva.setCosto(calcularCostoPorTipo(nueva.getTipoCita()));
        }

        String url = "http://ms-cita/cita/crear";
        try {
            return restTemplate.postForObject(url, nueva, Cita.class);
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            // Error 4xx al invocar ms-cita (por ejemplo 400, 404, 409)
            throw new RuntimeException("Error al invocar ms-cita: " + e.getMessage(), e);
        } catch (Exception e) {
            // Cualquier otro error inesperado
            throw new RuntimeException("Error inesperado al confirmar la cita: " + e.getMessage(), e);
        }
    }

    /**
     * Regla simple de negocio para costo según tipo de cita.
     * Base: 100. Se puede ajustar por especialidad en el futuro.
     */
    private double calcularCostoPorTipo(String tipoCita) {
        if (tipoCita == null) {
            return 100.0;
        }
        String tipo = tipoCita.toUpperCase();
        return switch (tipo) {
            case "EMERGENCIA" -> 200.0;
            case "CONTROL" -> 80.0;
            case "TELECONSULTA" -> 70.0;
            case "CONSULTA" -> 100.0;
            case "PRESENCIAL" -> 120.0;
            default -> 100.0;
        };
    }
}
