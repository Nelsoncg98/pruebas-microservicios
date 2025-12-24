package clinica.gestionboleta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@FeignClient(name = "ms-cajero")
public interface CajeroClient {
    @GetMapping("/cajero/buscar/{id}")
    Map<String, Object> buscar(@PathVariable Long id);
}
