package clinica.gestionreceta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@FeignClient(name = "ms-medico", url = "http://localhost:8091") 
public interface MedicoClient {
    @GetMapping("/medico/buscar/{id}")
    Map<String, Object> buscar(@PathVariable Long id);
}
