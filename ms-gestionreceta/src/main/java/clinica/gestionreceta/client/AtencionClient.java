package clinica.gestionreceta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@FeignClient(name = "ms-atencionmedica")
public interface AtencionClient {
    @GetMapping("/atencion/{id}")
    Map<String, Object> buscar(@PathVariable Long id);

    @org.springframework.web.bind.annotation.PutMapping("/atencion/actualizar-receta/{id}")
    Map<String, Object> actualizarReceta(@PathVariable Long id, @org.springframework.web.bind.annotation.RequestBody String recetaRef);
}
