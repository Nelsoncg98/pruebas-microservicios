package clinica.pagos.client;

import clinica.pagos.dto.PacienteDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PacienteClient {
    
    private final RestTemplate restTemplate;
    
    @Value("${microservicio.paciente.url:http://localhost:8092}")
    private String pacienteServiceUrl;
    
    public PacienteDto obtenerPaciente(Long pacienteId) {
        String url = pacienteServiceUrl + "/paciente/buscar/" + pacienteId;
        return restTemplate.getForObject(url, PacienteDto.class);
    }
}