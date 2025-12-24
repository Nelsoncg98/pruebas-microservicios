package clinica.gestionboleta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@FeignClient(name = "ms-boleta")
public interface BoletaClient {

    @GetMapping("/boleta/buscar/{id}")
    Map<String, Object> buscar(@PathVariable Long id);

    @PostMapping("/boleta/crear")
    Map<String, Object> crear(@RequestBody Object boleta);

    @PutMapping("/boleta/actualizar")
    Map<String, Object> actualizar(@RequestBody Object boleta);

    @DeleteMapping("/boleta/eliminar/{id}")
    void eliminar(@PathVariable Long id);
}
