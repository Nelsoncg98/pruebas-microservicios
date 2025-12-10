package clinica.gestionanalisis.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

@FeignClient(name = "ms-atencionmedica")
public interface AtencionClient {
    @GetMapping("/atencion/{id}")
    Map<String, Object> buscar(@PathVariable Long id);

    @PutMapping("/atencion/actualizar-analisis/{id}")
    Map<String, Object> actualizarAnalisis(@PathVariable Long id, @RequestBody String analisisRef);
}
