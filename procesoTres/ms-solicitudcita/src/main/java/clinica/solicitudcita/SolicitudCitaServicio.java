package clinica.solicitudcita;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Servicio compuesto para el proceso de solicitud de cita
@Service
public class SolicitudCitaServicio {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Lista médicos disponibles por especialidad, delegando en ms-medico.
     * Ejemplo de endpoint remoto:
     * http://localhost:8091/medico/porEspecialidad?especialidad=Cardiologia
     */
    public List<MedicoConHorarios> listarHorariosDisponiblesPorEspecialidad(String especialidad) {
        String urlMedicos = "http://ms-medico/medico/porEspecialidad?especialidad=" + especialidad;
        Medico[] medicos;
        try {
            medicos = restTemplate.getForObject(urlMedicos, Medico[].class);
        } catch (Exception e) {
            throw new RuntimeException("Error al invocar ms-medico: " + e.getMessage(), e);
        }
        if (medicos == null || medicos.length == 0) {
            return List.of();
        }

        List<MedicoConHorarios> resultado = new ArrayList<>();
        for (Medico m : medicos) {
            MedicoConHorarios dto = new MedicoConHorarios();
            dto.setNumero(m.getNumero());
            dto.setNombre(m.getNombre());
            dto.setEspecialidad(m.getEspecialidad());
            dto.setEmail(m.getEmail());
            dto.setTelefono(m.getTelefono());
            dto.setDni(m.getDni());
            dto.setApellido(m.getApellido());

            String urlHorarios = "http://ms-disponibilidadhorarios/disponibilidad/disponibles?medicoId=" + m.getNumero()
                    + "&disponible=true";
            HorarioMedico[] horarios;
            try {
                horarios = restTemplate.getForObject(urlHorarios, HorarioMedico[].class);
            } catch (Exception e) {
                throw new RuntimeException("Error al invocar ms-disponibilidadhorarios: " + e.getMessage(), e);
            }
            if (horarios != null && horarios.length > 0) {
                dto.setHorarios(Arrays.asList(horarios));
            } else {
                dto.setHorarios(List.of());
            }

            resultado.add(dto);
        }

        return resultado;
    }

    public CitasPorPaciente obtenerCitasPorPaciente(Long pacienteId) {
        if (pacienteId == null) {
            throw new IllegalArgumentException("pacienteId es obligatorio");
        }

        // 1) Obtener datos del paciente desde ms-paciente
        String urlPaciente = "http://ms-paciente/paciente/buscar/" + pacienteId;
        Paciente paciente;
        try {
            paciente = restTemplate.getForObject(urlPaciente, Paciente.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al invocar ms-paciente: " + e.getMessage(), e);
        }
        if (paciente == null) {
            throw new RuntimeException("Paciente no encontrado");
        }

        // 2) Obtener citas desde ms-cita
        String urlCitas = "http://ms-cita/cita/porPaciente/" + pacienteId;
        Cita[] citasArray;
        try {
            citasArray = restTemplate.getForObject(urlCitas, Cita[].class);
        } catch (Exception e) {
            throw new RuntimeException("Error al invocar ms-cita: " + e.getMessage(), e);
        }
        List<Cita> citas = (citasArray != null && citasArray.length > 0)
                ? Arrays.asList(citasArray)
                : List.of();

        CitasPorPaciente dto = new CitasPorPaciente();
        dto.setNumero(paciente.getNumero());
        dto.setNombre(paciente.getNombre());
        dto.setApellido(paciente.getApellido());
        dto.setDni(paciente.getDni());
        dto.setFechaNacimiento(paciente.getFechaNacimiento());
        dto.setTelefono(paciente.getTelefono());
        dto.setEmail(paciente.getEmail());
        dto.setDireccion(paciente.getDireccion());
        dto.setEstado(paciente.isEstado());
        dto.setCitas(citas);
        return dto;
    }

    /**
     * Confirma una cita médica orquestando la creación de la cita en ms-cita.
     * Si viene un costo explícito, se respeta; si no, se calcula en función del
     * tipoCita.
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

        // 0) Validar existencia de paciente, médico y horario antes de crear la cita
        String urlPaciente = "http://ms-paciente/paciente/buscar/" + idPaciente;
        try {
            Paciente paciente = restTemplate.getForObject(urlPaciente, Paciente.class);
            if (paciente == null) {
                throw new RuntimeException("Paciente no encontrado");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al invocar ms-paciente: " + e.getMessage(), e);
        }

        String urlMedico = "http://ms-medico/medico/buscar/" + idDoctor;
        try {
            Medico medico = restTemplate.getForObject(urlMedico, Medico.class);
            if (medico == null) {
                throw new RuntimeException("Médico no encontrado");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al invocar ms-medico: " + e.getMessage(), e);
        }

        String urlHorario = "http://ms-horariomedico/horariomedico/buscar/" + horarioId;
        try {
            HorarioMedico horario = restTemplate.getForObject(urlHorario, HorarioMedico.class);
            if (horario == null) {
                throw new RuntimeException("Horario no encontrado");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al invocar ms-horariomedico: " + e.getMessage(), e);
        }

        Cita nueva = new Cita();
        nueva.setPacienteId(idPaciente.longValue());
        nueva.setIdDoctor(String.valueOf(idDoctor));
        nueva.setHorarioId(horarioId.longValue());
        nueva.setMotivo(motivo != null ? motivo : "Solicitud de cita");
        nueva.setTipoCita(tipoCita != null ? tipoCita : "CONSULTA");
        nueva.setFecha(LocalDateTime.now());

        // Si el costo viene informado, se usa tal cual
        if (costoOpcional != null) {
            nueva.setCosto(costoOpcional);
        } else {
            // Caso contrario, se calcula en función del tipo de cita
            nueva.setCosto(calcularCostoPorTipo(nueva.getTipoCita()));
        }

        // 1) Crear cita en ms-cita
        String urlCita = "http://ms-cita/cita/crear";
        Cita creada;
        try {
            creada = restTemplate.postForObject(urlCita, nueva, Cita.class);
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            throw new RuntimeException("Error al invocar ms-cita: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al confirmar la cita: " + e.getMessage(), e);
        }

        // 2) Reservar horario en ms-horariomedico (usando POST en vez de PATCH)
        String urlReservar = "http://ms-horariomedico/horariomedico/reservar/" + horarioId;
        try {
            restTemplate.postForObject(urlReservar, null, Void.class);
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            throw new RuntimeException("Error al reservar horario: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al reservar el horario: " + e.getMessage(), e);
        }

        return creada;
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
