package clinica.gestionatencion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@FeignClient(name = "ms-paciente")
public interface PacienteClient {
    @GetMapping("/paciente/buscar/{id}")
    Map<String, Object> buscarPorId(@PathVariable Long id);
}
