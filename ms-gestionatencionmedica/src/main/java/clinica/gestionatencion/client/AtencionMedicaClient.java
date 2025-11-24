package clinica.gestionatencion.client;

import clinica.gestionatencion.dto.EntradaAtencion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "ms-atencionmedica")
public interface AtencionMedicaClient {
    @PostMapping("/atencion/registrar")
    Map<String, Object> registrar(@RequestBody EntradaAtencion atencion);
    
    @GetMapping("/atencion/{id}")
    Map<String, Object> buscarPorId(@PathVariable Long id);
}
