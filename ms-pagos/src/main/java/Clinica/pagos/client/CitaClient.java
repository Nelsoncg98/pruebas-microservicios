package clinica.pagos.client;

import clinica.pagos.dto.CitaDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CitaClient {
    
    private final RestTemplate restTemplate;
    
    @Value("${microservicio.cita.url:http://localhost:8089}")
    private String citaServiceUrl;
    
    public CitaDto obtenerCitaPorPaciente(Long pacienteId) {
        String url = citaServiceUrl + "/cita/porPaciente/" + pacienteId;
        return restTemplate.getForObject(url, CitaDto.class);
    }
}
