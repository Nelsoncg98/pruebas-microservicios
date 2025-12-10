package clinica.agregartipo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

@FeignClient(name = "ms-detalleanalisis")
public interface DetalleAnalisisClient {
    @PostMapping("/detalleanalisis/guardar")
    Map<String, Object> guardar(@RequestBody Map<String, Object> detalle);
}
