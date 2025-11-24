package clinica.expedienteclinico.clients;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import clinica.expedienteclinico.dto.AtencionMedicaDTO;


@FeignClient(name = "ms-atencion", url = "${clients.atencion.url:http://localhost:8097}")
public interface ClienteAtencionMedica {
    @GetMapping("/atencion/cita/{citaId}")
    List<AtencionMedicaDTO> obtenerPorCita(@PathVariable("citaId") Long citaId);
}

