package clinica.expedienteclinico.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import clinica.expedienteclinico.dto.HistoriaMedicaDTO;

@FeignClient(name = "ms-historia", url = "${clients.historia.url:http://localhost:8088}")
public interface ClienteHistoriaMedica {
    @GetMapping("/historiaMedica/buscar/paciente/{pacienteId}")
    HistoriaMedicaDTO buscarPorPacienteId(@PathVariable("pacienteId") Long pacienteId);
}
