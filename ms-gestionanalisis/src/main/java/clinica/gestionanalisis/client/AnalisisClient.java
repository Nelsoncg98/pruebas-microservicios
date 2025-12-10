package clinica.gestionanalisis.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@FeignClient(name = "ms-analisis")
public interface AnalisisClient {
    @PostMapping("/analisis/crear")
    Map<String, Object> crear(@RequestBody Map<String, Object> analisis);
    
    @GetMapping("/analisis/buscar/{id}")
    Map<String, Object> buscar(@PathVariable Long id);
    
    @PostMapping("/analisis/actualizarEstado/{id}")
    Map<String, Object> actualizarEstado(@PathVariable Long id, @RequestBody String estado);
}
