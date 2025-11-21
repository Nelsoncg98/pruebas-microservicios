package clinica.pagos.client;

import clinica.pagos.dto.CitaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import java.util.List;

/**
 * Cliente para consumir el microservicio de Citas
 */
@Service
public class CitaClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${microservicio.citas.url:http://localhost:8089}")
    private String citasServiceUrl;

    
    public List<CitaDto> obtenerCitasPorPaciente(Long pacienteId) {
        try {
            String url = citasServiceUrl + "/cita/porPaciente/" + pacienteId;
            ResponseEntity<List<CitaDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<CitaDto>>() {
                    });
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Error al obtener citas del paciente: " + pacienteId, e);
        }
    }

    
    public CitaDto obtenerCitaPorId(Long citaId) {
        try {
            String url = citasServiceUrl + "/cita/buscar/" + citaId;
            return restTemplate.getForObject(url, CitaDto.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Error al obtener cita con ID: " + citaId, e);
        }
    }

    /**
     * Verificar si existe una cita
     */
    public boolean existeCita(Long citaId) {
        try {
            CitaDto cita = obtenerCitaPorId(citaId);
            return cita != null;
        } catch (Exception e) {
            return false;
        }
    }
}
