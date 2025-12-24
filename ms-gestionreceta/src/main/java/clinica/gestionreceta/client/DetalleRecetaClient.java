package clinica.gestionreceta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Map;

@FeignClient(name = "ms-detallereceta")
public interface DetalleRecetaClient {
    @GetMapping("/detallereceta/listarPorReceta/{idReceta}")
    List<Map<String, Object>> listarPorReceta(@PathVariable Long idReceta);

    @DeleteMapping("/detallereceta/eliminar/{id}")
    void eliminar(@PathVariable Long id);
}
