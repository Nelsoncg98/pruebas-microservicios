package clinica.pagos.client;

import clinica.pagos.dto.PacienteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

/**
 * Cliente para consumir el microservicio de Pacientes
 */
@Service
public class PacienteClient {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${microservicio.pacientes.url:http://localhost:8092}")
    private String pacientesServiceUrl;
    
    /**
     * Obtener paciente por ID
     * GET http://localhost:8092/paciente/buscar/{id}
     */
    public PacienteDto obtenerPacientePorId(Long pacienteId) {
        try {
            String url = pacientesServiceUrl + "/paciente/buscar/" + pacienteId;
            return restTemplate.getForObject(url, PacienteDto.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Error al obtener paciente con ID: " + pacienteId, e);
        }
    }
    
    /**
     * Verificar si existe un paciente
     */
    public boolean existePaciente(Long pacienteId) {
        try {
            PacienteDto paciente = obtenerPacientePorId(pacienteId);
            return paciente != null;
        } catch (Exception e) {
            return false;
        }
    }
}
