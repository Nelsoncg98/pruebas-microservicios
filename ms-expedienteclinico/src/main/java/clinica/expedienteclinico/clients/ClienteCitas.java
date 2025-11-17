package clinica.expedienteclinico.clients;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import clinica.expedienteclinico.dto.CitaDTO;

@FeignClient(name = "ms-citas", url = "${clients.citas.url:http://localhost:8089}")
public interface ClienteCitas {
    @GetMapping("/cita/porPaciente/{pacienteId}")
    List<CitaDTO> listarPorPaciente(@PathVariable("pacienteId") Long pacienteId);
}
