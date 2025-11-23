package clinica.carritohorario;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CarritoHorarioMedicoServicio {
    private static final Logger log = LoggerFactory.getLogger(CarritoHorarioMedicoServicio.class);
    @Autowired
    private CarritoHorarioMedicoRepositorio repo;

    @Autowired
    private RestTemplate resTem;

    public void verificarMedico(Long medicoId) {
        try {
            Object medico = resTem.getForObject(
                "http://ms-medico/medico/buscar/{id}",
                Object.class,
                medicoId
            );
            if (medico == null) {
                throw new IllegalArgumentException("El médico con id=" + medicoId + " no existe");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            throw new IllegalArgumentException("El médico con id=" + medicoId + " no existe");
        } catch (HttpClientErrorException ex){
            throw new IllegalStateException("Error al consultar ms-medico: " + ex.getStatusCode());
        } catch (HttpServerErrorException ex){
            throw new IllegalStateException("Falla interna en ms-medico: " + ex.getStatusCode());
        }
    }

    public Linea agregar(Linea horario) {
        log.info("[Carrito] Solicitud agregar horario: fecha={}, inicio={}, fin={}, medicoId={}, consultorio={}",
            horario.getFecha(), horario.getHoraInicio(), horario.getHoraFin(), horario.getMedicoId(), horario.getConsultorio());
        // 1) Verificamos que el médico exista en el microservicio ms-medico
        verificarMedico(horario.getMedicoId());

        // 2) Verificamos que el horario sea del mismo médico que los ya existentes en el carrito
        verificarMedicoUnico(horario);

        // 3) Verificamos que el nuevo horario NO se solape con otros
        //    horarios ya agregados al carrito.
        verificarConflictos(horario);

        // 4) Si todo está correcto, recién guardamos la línea en el carrito
        return repo.save(horario);
    }

    /**
     * Verifica que el nuevo horario sea del mismo médico que los horarios
     * ya existentes en el carrito. Si el carrito está vacío, permite cualquier médico.
     * 
     * Regla de negocio: Una programación médica es para UN SOLO médico,
     * por lo tanto el carrito solo puede contener horarios de un mismo médico.
     */
    private void verificarMedicoUnico(Linea nuevo) {
        List<Linea> existentes = repo.findAll();
        
        if (existentes.isEmpty()) {
            // Carrito vacío, permite cualquier médico
            log.info("[Carrito] Carrito vacío, se permite agregar horario del médico ID={}", nuevo.getMedicoId());
            return;
        }
        
        // Obtener el medicoId del primer horario en el carrito
        Long medicoEnCarrito = existentes.get(0).getMedicoId();
        
        if (medicoEnCarrito == null || nuevo.getMedicoId() == null) {
            throw new IllegalStateException("El horario debe tener un médico asignado");
        }
        
        if (!medicoEnCarrito.equals(nuevo.getMedicoId())) {
            String mensaje = String.format(
                "El carrito ya contiene horarios del médico ID=%d. " +
                "No se pueden agregar horarios de otro médico (ID=%d). " +
                "Vacíe el carrito primero.",
                medicoEnCarrito,
                nuevo.getMedicoId()
            );
            log.warn("[Carrito] {}", mensaje);
            throw new IllegalStateException(mensaje);
        }
        
        log.info("[Carrito] Validación OK: horario del médico ID={} coincide con el carrito", nuevo.getMedicoId());
    }

    /**
     * Verifica que el nuevo horario no se solape con otros horarios
     * que ya están en el carrito.
     *
     * Regla de negocio (simplificada y didáctica):
     * - No permitimos dos horarios que se cruzan en el tiempo
     *   para el mismo médico o el mismo consultorio.
     * - Sí permitimos que un horario termine exactamente cuando
     *   el otro empieza (ej: 11:00-12:00 y 12:00-13:00).
     */
    private void verificarConflictos(Linea nuevo) {
        // 1) Validamos contra los horarios que ya están en el carrito
        List<Linea> existentes = repo.findAll();

        for (Linea actual : existentes) {
            // 1) Mismo médico o mismo consultorio
            boolean mismoMedico = actual.getMedicoId() != null
                && actual.getMedicoId().equals(nuevo.getMedicoId());

            boolean mismoConsultorio = actual.getConsultorio() != null
                && actual.getConsultorio().equalsIgnoreCase(nuevo.getConsultorio());

            if (!(mismoMedico || mismoConsultorio)) {
                // Si no comparten ni médico ni consultorio, no nos preocupa
                continue;
            }

            // 2) Comparamos las horas como intervalos [inicio, fin)
            //    Usamos la regla clásica de intervalos abiertos por la derecha:
            //    dos intervalos se solapan si inicioNuevo < finExistente
            //    Y inicioExistente < finNuevo.

            // Nota: asumimos que las horas ya vienen validadas (inicio < fin)
            // en el microservicio que crea la línea o en la capa de entrada.

            // Si alguno de los campos de hora es nulo, no podemos comparar
            if (actual.getHoraInicio() == null || actual.getHoraFin() == null
                || nuevo.getHoraInicio() == null || nuevo.getHoraFin() == null) {
                continue;
            }

            boolean solapan =
                nuevo.getHoraInicio().isBefore(actual.getHoraFin()) &&
                actual.getHoraInicio().isBefore(nuevo.getHoraFin());

            if (solapan) {
                String mensaje = String.format(
                    "Conflicto de horario en carrito. Médico %d, consultorio %s. " +
                    "Horario nuevo %s-%s se solapa con horario existente %s-%s.",
                    nuevo.getMedicoId(),
                    nuevo.getConsultorio(),
                    nuevo.getHoraInicio(),
                    nuevo.getHoraFin(),
                    actual.getHoraInicio(),
                    actual.getHoraFin()
                );
                log.warn("[Carrito] {}", mensaje);
                throw new IllegalStateException(mensaje);
            }
        }

        // 2) Validamos también contra los horarios definitivos en ms-horariomedico
        //    (por ejemplo, horarios ya publicados del mismo médico en la misma fecha).
        verificarConflictosConHorariosDefinitivos(nuevo);
    }

    /**
     * Consulta ms-horariomedico y verifica que el horario del carrito
     * no se solape con horarios definitivos ya registrados.
     *
     * Para mantener el ejemplo sencillo, usamos un GET general a
     * /horarios y filtramos en memoria por médico/consultorio/fecha.
     * En un proyecto real convendría tener un endpoint filtrado
     * (por ejemplo /horarios/buscar?medicoId=&fecha=).
     */
    private void verificarConflictosConHorariosDefinitivos(Linea nuevo) {
        try {
            // Obtenemos todos los horarios definitivos desde ms-horariomedico
            log.info("[Carrito] Consultando horarios definitivos en ms-horariomedico para fecha={}, medicoId={}, consultorio={}",
                    nuevo.getFecha(), nuevo.getMedicoId(), nuevo.getConsultorio());
            HorarioMedicoRemoto[] horarios = resTem.getForObject(
                "http://ms-horariomedico/horariomedico/listar",
                HorarioMedicoRemoto[].class
            );

            if (horarios == null) {
                log.info("[Carrito] ms-horariomedico/horario/listar devolvió null (sin horarios)");
                return; // nada que validar
            }

            log.info("[Carrito] ms-horariomedico devolvió {} horarios", horarios.length);

            for (HorarioMedicoRemoto actual : horarios) {
                // 1) Misma fecha (mismo día)
                boolean mismaFecha = actual.getFecha() != null
                    && actual.getFecha().equals(nuevo.getFecha());

                if (!mismaFecha) {
                    // Si la fecha no coincide, no hay conflicto para este día
                    continue;
                }

                // 2) Mismo médico o mismo consultorio
                boolean mismoMedico = actual.getMedicoId() != null
                    && actual.getMedicoId().equals(nuevo.getMedicoId());

                boolean mismoConsultorio = actual.getConsultorio() != null
                    && actual.getConsultorio().equalsIgnoreCase(nuevo.getConsultorio());

                if (!(mismoMedico || mismoConsultorio)) {
                    continue;
                }

                // 3) Validamos las horas como intervalos [inicio, fin)
                if (actual.getHoraInicio() == null || actual.getHoraFin() == null
                    || nuevo.getHoraInicio() == null || nuevo.getHoraFin() == null) {
                    continue;
                }

                boolean solapan =
                    nuevo.getHoraInicio().isBefore(actual.getHoraFin()) &&
                    actual.getHoraInicio().isBefore(nuevo.getHoraFin());

                if (solapan) {
                    String mensaje = String.format(
                        "Conflicto con horario definitivo. Fecha %s. Médico %d, consultorio %s. " +
                        "Horario de carrito %s-%s se solapa con horario definitivo %s-%s.",
                        nuevo.getFecha(),
                        nuevo.getMedicoId(),
                        nuevo.getConsultorio(),
                        nuevo.getHoraInicio(),
                        nuevo.getHoraFin(),
                        actual.getHoraInicio(),
                        actual.getHoraFin()
                    );
                    log.warn("[Carrito] {}", mensaje);
                    throw new IllegalStateException(mensaje);
                }
            }
        } catch (HttpClientErrorException.NotFound ex) {
            // Si ms-horariomedico devuelve 404, lo interpretamos como
            // "no hay horarios definitivos" y no como error.
            log.info("[Carrito] ms-horariomedico/horario/listar devolvió 404 (sin horarios definitivos)");
            return;
        } catch (HttpClientErrorException ex) {
            // Otros errores 4xx los propagamos como estado de negocio.
            throw new IllegalStateException("Error al consultar ms-horariomedico: " + ex.getStatusCode());
        } catch (HttpServerErrorException ex) {
            throw new IllegalStateException("Falla interna en ms-horariomedico: " + ex.getStatusCode());
        }
    }

    // DTO local sencillo para leer horarios desde ms-horariomedico
    // No compartimos la entidad JPA entre microservicios, solo la forma del JSON.
    private static class HorarioMedicoRemoto {
        private java.time.LocalDate fecha;
        private java.time.LocalTime horaInicio;
        private java.time.LocalTime horaFin;
        private Long medicoId;
        private String consultorio;

        public java.time.LocalDate getFecha() { return fecha; }
        public void setFecha(java.time.LocalDate fecha) { this.fecha = fecha; }
        public java.time.LocalTime getHoraInicio() { return horaInicio; }
        public void setHoraInicio(java.time.LocalTime horaInicio) { this.horaInicio = horaInicio; }
        public java.time.LocalTime getHoraFin() { return horaFin; }
        public void setHoraFin(java.time.LocalTime horaFin) { this.horaFin = horaFin; }
        public Long getMedicoId() { return medicoId; }
        public void setMedicoId(Long medicoId) { this.medicoId = medicoId; }
        public String getConsultorio() { return consultorio; }
        public void setConsultorio(String consultorio) { this.consultorio = consultorio; }
    }

    public void quitar(Long id) {
        repo.deleteById(id);
    }

    public List<Linea> listar() {
        return repo.findAll();
    }

    public double total() {
        // total sencillo: cantidad de líneas
        return repo.findAll().size();
    }

    public void nuevo() {
        repo.deleteAll();
    }
}
