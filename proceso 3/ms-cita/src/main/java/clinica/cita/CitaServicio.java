package clinica.cita;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CitaServicio {

    @Autowired
    private CitaRepositorio repo;

    @Autowired
    private RestTemplate resTem;

    public List<Cita> listar(){ return repo.findAll(); }

    public Cita buscar(Long id){ return repo.findById(id).orElse(null); }

    public Cita guardar(Cita c){ return repo.save(c); }

    public Cita crear(String dniPaciente, Long pacienteId, Long horarioId){
        // 1) Resolver pacienteId vía ms-paciente si sólo nos pasan DNI
        Long pid = pacienteId;
        if (pid == null && dniPaciente != null && !dniPaciente.isBlank()){
            try{
                Map<?,?> p = resTem.getForObject("http://ms-paciente/paciente?dni={dni}", Map.class, dniPaciente);
                Object num = p.get("numero");
                if (num instanceof Number n){
                    pid = n.longValue();
                } else {
                    throw new IllegalStateException("Paciente no encontrado");
                }
            } catch (HttpClientErrorException.NotFound ex){
                throw new IllegalStateException("Paciente no encontrado");
            }
        }
        if (pid == null){
            throw new IllegalArgumentException("Se requiere pacienteId o dniPaciente");
        }
        if (horarioId == null){
            throw new IllegalArgumentException("Se requiere horarioId");
        }

        // 2) Reservar el horario en ms-horariomedico
        try{
            resTem.patchForObject(
                "http://ms-horariomedico/horariomedico/reservar/{id}", null, Map.class, horarioId);
        } catch (HttpClientErrorException.Conflict ex){
            throw new IllegalStateException("El horario ya está reservado/no disponible");
        } catch (HttpClientErrorException.NotFound ex){
            throw new IllegalStateException("Horario no encontrado");
        }

        // 3) Persistir la cita
        Cita c = new Cita();
        c.setPacienteId(pid);
        c.setHorarioId(horarioId);
        c.setEstado("RESERVADA");
        return repo.save(c);
    }

    public Cita cancelar(Long id){
        Optional<Cita> op = repo.findById(id);
        if (op.isEmpty()) return null;
        Cita c = op.get();
        // 1) Liberar el horario relacionado
        try{
            resTem.patchForObject("http://ms-horariomedico/horariomedico/liberar/{id}", null, Map.class, c.getHorarioId());
        } catch (HttpClientErrorException ex){
            // Si falla la liberación, aún marcamos la cita como cancelada para no dejarla activa
        }
        // 2) Cambiar estado
        c.setEstado("CANCELADA");
        return repo.save(c);
    }
}
