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

    public List<HorarioMedico> disponibles(LocalDate fecha, Long medicoId, String consultorio, Boolean disponible) {
        // Si no se pasa el parámetro 'disponible', por defecto lo consideramos true
        if (disponible == null) {
            disponible = Boolean.TRUE;
        }

        // Si se envía medicoId, primero validar que el médico exista
        if (medicoId != null && !existeMedicoPorId(medicoId)) {
            return List.of();
        }

        List<HorarioMedico> base;

        // Usar consultas específicas que incluyan el flag 'disponible' cuando sea posible
        if (fecha != null && medicoId != null) {
            base = repo.findByFechaAndMedicoIdAndDisponible(fecha, medicoId, disponible);
        } else if (fecha != null) {
            base = repo.findByFechaAndDisponible(fecha, disponible);
        } else if (medicoId != null) {
            base = repo.findByMedicoIdAndDisponible(medicoId, disponible);
        } else {
            base = repo.findByDisponible(disponible);
        }

        // Filtrar por consultorio si se especificó
        if (consultorio != null && !consultorio.isBlank()) {
            base = base.stream()
                    .filter(h -> consultorio.equalsIgnoreCase(h.getConsultorio())).toList();
        }

        return base;
    }

    public boolean existeMedicoPorId(Long id){
        try{
            Object resp = resTem.getForObject("http://ms-medico/medico/buscar/{id}", Object.class, id);
            return resp != null;
        } catch (RestClientException ex){
            return false;
        }
    }

    public List<Linea> medicosDisponibles(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        // obtiene todos los horarios disponibles en el rango indicado
        List<HorarioMedico> horarios = repo.findByFecha(fecha);

    // filtra por disponibilidad y por horaInicio/horaFin proporcionados
    horarios = horarios.stream()
        .filter(h -> Boolean.TRUE.equals(h.getDisponible())) // sólo horarios disponibles
        .filter(h -> !h.getHoraInicio().isAfter(horaFin) && !h.getHoraFin().isBefore(horaInicio))
        .collect(Collectors.toList());

        // Extrae los IDs únicos de los médicos libres
        List<Long> medicosLibres = horarios.stream()
                .map(HorarioMedico::getMedicoId)
                .distinct()
                .collect(Collectors.toList());


    // lista de medicos con rest template — ruta correcta en ms-medico es '/medico/listar'
    // usamos el serviceId para que el RestTemplate @LoadBalanced lo resuelva via Eureka
    String url = "http://ms-medico/medico/listar";

    Linea[] medicos = resTem.getForObject(url, Linea[].class);

        // Si el servicio de médicos respondió null o vacío, devolver lista vacía
        if (medicos == null || medicos.length == 0) {
            return List.of();
        }

        // Filtrar médicos disponibles
        return Arrays.stream(medicos)
                .filter(m -> medicosLibres.contains(m.getNumero()))
                .collect(Collectors.toList());

    }

    // Cambia el flag disponible=false si está true; lanza IllegalStateException si ya no está disponible
    public HorarioMedico reservar(Long id){
        Optional<HorarioMedico> op = repo.findById(id);
        if (op.isEmpty()) return null; // 404 en el controlador
        HorarioMedico h = op.get();
        if (Boolean.FALSE.equals(h.getDisponible())){
            throw new IllegalStateException("El horario ya está reservado/no disponible");
        }
        h.setDisponible(Boolean.FALSE);
        return repo.save(h);
    }

    // Cambia el flag disponible=true si está false; lanza IllegalStateException si ya está disponible
    public HorarioMedico liberar(Long id){
        Optional<HorarioMedico> op = repo.findById(id);
        if (op.isEmpty()) return null; // 404 en el controlador
        HorarioMedico h = op.get();
        if (Boolean.TRUE.equals(h.getDisponible())){
            throw new IllegalStateException("El horario ya está disponible");
        }
        h.setDisponible(Boolean.TRUE);
        return repo.save(h);
    }

}
