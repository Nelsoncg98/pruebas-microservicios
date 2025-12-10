package clinica.agregarmedicamento.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

@FeignClient(name = "ms-detallereceta")
public interface DetalleRecetaClient {
    @PostMapping("/detallereceta/guardar")
    Map<String, Object> guardar(@RequestBody Map<String, Object> detalle);
}
