package clinica.gestionboleta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@FeignClient(name = "ms-paciente")
public interface PacienteClient {
    @GetMapping("/paciente/buscar/{id}")
    Map<String, Object> buscar(@PathVariable Long id);
    
    // Buscar por DNI si es necesario
    @GetMapping("/paciente/dni/{dni}")
    Map<String, Object> buscarPorDni(@PathVariable String dni);
}
