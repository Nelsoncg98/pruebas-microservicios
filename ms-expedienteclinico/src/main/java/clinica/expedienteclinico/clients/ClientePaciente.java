package clinica.expedienteclinico.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import clinica.expedienteclinico.dto.PacienteDTO;

@FeignClient(name = "ms-paciente", url = "${clients.paciente.url:http://localhost:8092}")
public interface ClientePaciente {
    @GetMapping("/paciente/buscar/{id}")
    PacienteDTO buscarPorId(@PathVariable("id") Long id);
}

