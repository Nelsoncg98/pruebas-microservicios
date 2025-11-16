package clinica.disponibilidadhorarios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import clinica.disponibilidadhorarios.HorarioMedicoEntrada;

@Service
public class DisponibilidadHorariosServicio {

    @Autowired
    private RestTemplate resTem;

    /**
     * Proceso compuesto: lista horarios disponibles aplicando filtros opcionales.
     * Equivale a lo que antes hacía /horariomedico/disponibles pero ahora
     * concentrado en el microservicio compuesto.
     */
    public List<HorarioMedicoEntrada> horariosDisponibles(LocalDate fecha, Long medicoId, String consultorio, Boolean disponible) {
        String url = "http://ms-horariomedico/horariomedico/listar";

        // Cargamos todos los horarios y filtramos aquí. Si el volumen crece, se
        // podría optimizar con parámetros en el propio ms-horariomedico.
        HorarioMedicoEntrada[] todos;
        try {
            todos = resTem.getForObject(url, HorarioMedicoEntrada[].class);
        } catch (Exception e) {
            throw new RuntimeException("Error al invocar ms-horariomedico: " + e.getMessage(), e);
        }
        if (todos == null || todos.length == 0) {
            return List.of();
        }

        if (disponible == null) {
            disponible = Boolean.TRUE;
        }

        List<HorarioMedicoEntrada> filtrados = new java.util.ArrayList<>();
        for (HorarioMedicoEntrada horario : todos) {
            if (!Boolean.valueOf(disponible).equals(horario.getDisponible())) {
                continue;
            }
            if (fecha != null && !fecha.equals(horario.getFecha())) {
                continue;
            }
            if (medicoId != null && !medicoId.equals(horario.getMedicoId())) {
                continue;
            }
            if (consultorio != null && !consultorio.isBlank()) {
                if (horario.getConsultorio() == null || !consultorio.equalsIgnoreCase(horario.getConsultorio())) {
                    continue;
                }
            }
            filtrados.add(horario);
        }
        return filtrados;
    }

}
