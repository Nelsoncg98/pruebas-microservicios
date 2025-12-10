package clinica.gestionreceta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@FeignClient(name = "ms-medicamento", url = "http://localhost:8095")
public interface MedicamentoClient {
    @GetMapping("/medicamento/buscar/{id}")
    Map<String, Object> buscar(@PathVariable Long id);
}
