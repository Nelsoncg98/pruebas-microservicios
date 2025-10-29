package clinica.horariomedico;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class HorarioMedicoServicio {

    @Autowired
    private RestTemplate resTem;

    @Autowired
    private HorarioMedicoRepositorio repo;
    

    public List<HorarioMedico> listar() {
        return repo.findAll();
    }

    public HorarioMedico guardar(HorarioMedico h) {
        if (h.getMedicoId() == null) {
            throw new IllegalArgumentException("El campo medicoId es obligatorio");
        }
        boolean existe = existeMedicoPorId(h.getMedicoId());
        if (!existe) {
            throw new IllegalArgumentException("El médico con id=" + h.getMedicoId() + " no existe en ms-medico");
        }
        // Valor por defecto robusto: si no viene en el payload, marcar disponible=true
        if (h.getDisponible() == null) {
            h.setDisponible(Boolean.TRUE);
        }
        return repo.save(h);
    }

    public HorarioMedico buscar(Long id) {
        Optional<HorarioMedico> op = repo.findById(id);
        return op.orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public HorarioMedico actualizar(Long id, HorarioMedico h) {
        h.setNumero(id);
        return repo.save(h);
    }

    public void limpiar() {
        repo.deleteAll();
    }

    public List<HorarioMedico> disponibles(LocalDate fecha, Long medicoId, String consultorio) {
        // Estrategia por ID: valida ms-medico si se envía medicoId y filtra por
        // disponible=true

        List<HorarioMedico> base;

        // Si no existe el médico, retorna lista vacía
        if (medicoId != null && !existeMedicoPorId(medicoId)) {
            return List.of();
        }

        if (fecha != null && medicoId != null) {
            base = repo.findByFechaAndMedicoId(fecha, medicoId);
        } else if (fecha != null) {
            base = repo.findByFecha(fecha);
        } else if (medicoId != null) {
            base = repo.findByMedicoId(medicoId);
        } else {
            base = repo.findAll();
        }

        if (consultorio != null && !consultorio.isBlank()) {
            base = base.stream()
                    .filter(h -> consultorio.equalsIgnoreCase(h.getConsultorio())).toList();
        }

        return base;
    }

    public boolean existeMedicoPorId(Long id){
        try{
            RestTemplate plain = new RestTemplate(); // no LoadBalancer
            Object resp = plain.getForObject("http://localhost:8091/medico/buscar/{id}", Object.class, id);
            return resp != null;
        } catch (RestClientException ex){
            return false;
        }
    }

    public List<Linea> medicosDisponibles(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        // obtiene todos los horarios disponibles en el rango indicado
        List<HorarioMedico> horarios = repo.findByFecha(fecha);

        // filtra por horaInicio y horaFin proporcionados
        horarios = horarios.stream()
                .filter(h -> !h.getHoraInicio().isAfter(horaFin) && !h.getHoraFin().isBefore(horaInicio))
                .collect(Collectors.toList());

        // Extrae los IDs únicos de los médicos libres
        List<Long> medicosLibres = horarios.stream()
                .map(HorarioMedico::getMedicoId)
                .distinct()
                .collect(Collectors.toList());


        // lista de medicos con rest template
        String url = "http://ms-medico/medicos/listar";

        Linea[] medicos = resTem.getForObject(url, Linea[].class);

        // Filtrar médicos disponibles
        return Arrays.stream(medicos)
                .filter(m -> medicosLibres.contains(m.getNumero()))
                .collect(Collectors.toList());

    }

}
