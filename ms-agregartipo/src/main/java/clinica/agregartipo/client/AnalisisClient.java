package clinica.agregartipo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@FeignClient(name = "ms-analisis")
public interface AnalisisClient {
    @GetMapping("/analisis/buscar/{id}")
    Map<String, Object> buscar(@PathVariable Long id);
}
