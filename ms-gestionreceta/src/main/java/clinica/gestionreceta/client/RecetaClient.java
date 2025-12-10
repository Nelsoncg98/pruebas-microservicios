package clinica.gestionreceta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

@FeignClient(name = "ms-receta")
public interface RecetaClient {
    @PostMapping("/receta/crear")
    Map<String, Object> crear(@RequestBody Map<String, Object> body);

    @GetMapping("/receta/buscar/{id}")
    Map<String, Object> buscar(@PathVariable Long id);

    @PostMapping("/receta/actualizarEstado/{id}")
    Map<String, Object> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body);
}
