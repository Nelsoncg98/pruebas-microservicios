package clinica.gestionanalisis.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Map;

@FeignClient(name = "ms-detalleanalisis")
public interface DetalleAnalisisClient {
    @GetMapping("/detalleanalisis/listarPorAnalisis/{id}")
    List<Map<String, Object>> listarPorAnalisis(@PathVariable Long id);
}
