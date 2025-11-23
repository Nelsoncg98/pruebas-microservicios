package clinica.programacioncompuesta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class ProgramacionCompuestaServicio {

    @Autowired
    private RestTemplate resTem;
    

    // ==== Verificaciones básicas de entidades remotas ====
    // Se consulta ms-personaladministrativo y ms-medico para asegurarse
    // de que los IDs usados en el proceso compuesto existan.

    public void verificarAdministrativo(Long idAdministrativo) {
        try {
            Object administrativo = resTem.getForObject(
                "http://ms-personaladministrativo/personaladministrativo/buscar/{id}",
                Object.class,
                idAdministrativo
            );
            if (administrativo == null) {
                throw new IllegalArgumentException("El administrativo con id=" + idAdministrativo + " no existe");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            throw new IllegalArgumentException("El administrativo con id=" + idAdministrativo + " no existe");
        } catch (HttpClientErrorException ex){
            throw new IllegalStateException("Error al consultar ms-personaladministrativo: " + ex.getStatusCode());
        } catch (HttpServerErrorException ex){
            throw new IllegalStateException("Falla interna en ms-personaladministrativo: " + ex.getStatusCode());
        }
    }

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

    // ==== Utilidades internas ====
    // Fecha de hoy en el formato que usa ProgramacionMedica (dd/MM/yyyy).
    private String fechaHoy() {
        Date dat = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dat);
    }

    // ==== Punto de entrada: crear programación compuesta ====
    // 1) Valida el administrativo
    // 2) Toma los horarios del carrito
    // 3) Persiste cada horario en ms-horariomedico
    // 4) Crea la ProgramacionMedica en ms-programacionmedica
    // 5) Limpia el carrito
    // 6) Devuelve ProgramacionMedica enriquecida con la lista de horarios
    // 7) Además se enriquece con el objeto administrativo completo y médico
    public ProgramacionMedica nuevaProgramacion(Long idAdministrativo){
        verificarAdministrativo(idAdministrativo);

        HorarioMedico[] horarios = obtenerHorariosDeCarrito();
        if (horarios == null || horarios.length == 0){
            throw new IllegalStateException("El carrito de horarios está vacío");
        }

        List<HorarioMedico> horariosGuardados = guardarHorariosEnServicio(horarios);
        ProgramacionMedica programacion = crearProgramacionMedica(idAdministrativo, horariosGuardados);
        limpiarCarrito();
        
        // Enriquecer con el objeto médico (del primer horario)
        if (!horariosGuardados.isEmpty() && horariosGuardados.get(0).getMedicoId() != null) {
            programacion.setMedico(obtenerMedicoPorId(horariosGuardados.get(0).getMedicoId()));
        }
        
        // Enriquecer con el objeto administrativo completo
        programacion.setAdministrativo(obtenerAdministrativoPorId(idAdministrativo));
        
        // Agregar total de horarios
        programacion.setTotalHorarios(horariosGuardados.size());

        return programacion;

    }

    

    // ==== Bloque de llamadas a ms-carritohorariomedico ====
    private HorarioMedico[] obtenerHorariosDeCarrito() {
        try {
            return resTem.getForObject(
                "http://ms-carritohorariomedico/carritohorario/listar",
                HorarioMedico[].class
            );
        } catch (HttpClientErrorException | HttpServerErrorException ex){
            throw new IllegalStateException("No se pudo obtener el carrito de horarios (" + ex.getStatusCode() + ")");
        }
    }

    // Persiste cada HorarioMedico en ms-horariomedico y devuelve la lista
    // de horarios ya guardados (con su número/id definitivo).
    private List<HorarioMedico> guardarHorariosEnServicio(HorarioMedico[] horarios){
        List<HorarioMedico> guardados = new ArrayList<>();
        for (HorarioMedico h : horarios){
            try {
                HorarioMedico guardado = resTem.postForObject(
                    "http://ms-horariomedico/horariomedico/guardar",
                    h,
                    HorarioMedico.class
                );
                if (guardado != null){
                    guardados.add(guardado);
                }
            } catch (HttpClientErrorException | HttpServerErrorException ex){
                throw new IllegalStateException("Error al guardar horario médico en ms-horariomedico (" + ex.getStatusCode() + ")");
            }
        }
        return guardados;
    }

    // Construye un ProgramacionMedica con los datos mínimos y los ids de
    // horarios, lo envía a ms-programacionmedica y devuelve el objeto
    // ya creado, enriquecido con la lista de horarios.
    private ProgramacionMedica crearProgramacionMedica(Long idAdministrativo, List<HorarioMedico> horariosGuardados){
        List<Long> idsGuardados = new ArrayList<>();
        for (HorarioMedico h : horariosGuardados){
            idsGuardados.add(h.getNumero());
        }

        ProgramacionMedica prog = new ProgramacionMedica(null, idAdministrativo, fechaHoy(), true, idsGuardados);

        try {
            ProgramacionMedica creada = resTem.postForObject(
                "http://ms-programacionmedica/programacionmedica/guardar",
                prog,
                ProgramacionMedica.class
            );
            if (creada == null){
                throw new IllegalStateException("ms-programacionmedica devolvió nulo al guardar programación");
            }
            creada.setHorarios(horariosGuardados);
            return creada;
        } catch (HttpClientErrorException | HttpServerErrorException ex){
            throw new IllegalStateException("Error al registrar la programación médica en ms-programacionmedica (" + ex.getStatusCode() + ")");
        }
    }

    // Intenta limpiar el carrito remoto. Si falla, se informa con un error
    // para que la UI/cliente pueda reaccionar.
    private void limpiarCarrito(){
        try {
            resTem.delete("http://ms-carritohorariomedico/carritohorario/nuevo");
        } catch (HttpClientErrorException | HttpServerErrorException ex){
            throw new IllegalStateException("No se pudo limpiar el carrito de horarios (" + ex.getStatusCode() + ")");
        }
    }

    // ===============================================================

    // ==== Punto de entrada: buscar programación compuesta ====
    // Se obtiene la ProgramacionMedica desde el servicio de entidad y
    // luego se resuelven los HorarioMedico completos por cada id.
    // Además se enriquece con el objeto administrativo completo y médico
    public ProgramacionMedica buscarProgramacion(Long id){
        ProgramacionMedica programacion = obtenerProgramacionMedica(id);
        List<HorarioMedico> horarios = obtenerHorariosPorIds(programacion.getHorarioMedicoIds());
        
        programacion.setHorarios(horarios);
        
        // Enriquecer con el objeto administrativo completo
        if (programacion.getAdministrativoId() != null) {
            Object administrativo = obtenerAdministrativoPorId(programacion.getAdministrativoId());
            programacion.setAdministrativo(administrativo);
        }
        
        // Enriquecer con el objeto médico (solo UNO para toda la programación)
        // Tomamos el medicoId del primer horario ya que todos son del mismo médico
        if (!horarios.isEmpty() && horarios.get(0).getMedicoId() != null) {
            Object medico = obtenerMedicoPorId(horarios.get(0).getMedicoId());
            programacion.setMedico(medico);
        }
        
        // Agregar total de horarios
        programacion.setTotalHorarios(horarios.size());
        
        return programacion;
    }

    // Recupera la ProgramacionMedica simple (sin horarios) desde el
    // microservicio de entidad ms-programacionmedica.
    private ProgramacionMedica obtenerProgramacionMedica(Long id){
        try {
            return resTem.getForObject(
                "http://ms-programacionmedica/programacionmedica/buscar/{id}",
                ProgramacionMedica.class,
                id
            );
        } catch (HttpClientErrorException.NotFound ex){
            throw new IllegalArgumentException("Programación no encontrada");
        } catch (HttpClientErrorException | HttpServerErrorException ex){
            throw new IllegalStateException("Error al consultar ms-programacionmedica (" + ex.getStatusCode() + ")");
        }
    }

    // A partir de la lista de ids, consulta ms-horariomedico para
    // obtener cada HorarioMedico completo.
    private List<HorarioMedico> obtenerHorariosPorIds(List<Long> ids){
        List<HorarioMedico> horarios = new ArrayList<>();
        if (ids == null){
            return horarios;
        }
        for (Long hid : ids){
            try {
                HorarioMedico h = resTem.getForObject(
                    "http://ms-horariomedico/horariomedico/buscar/{id}",
                    HorarioMedico.class,
                    hid
                );
                if (h != null){
                    horarios.add(h);
                }
            } catch (HttpClientErrorException.NotFound ex){
                throw new IllegalArgumentException("Horario médico con id=" + hid + " no encontrado");
            } catch (HttpClientErrorException | HttpServerErrorException ex){
                throw new IllegalStateException("Error al consultar ms-horariomedico (" + ex.getStatusCode() + ")");
            }
        }
        return horarios;
    }

    // Obtiene el objeto administrativo completo desde ms-personaladministrativo
    private Object obtenerAdministrativoPorId(Long id){
        try {
            return resTem.getForObject(
                "http://ms-personaladministrativo/personaladministrativo/buscar/{id}",
                Object.class,
                id
            );
        } catch (HttpClientErrorException | HttpServerErrorException ex){
            // Si falla, retornar null en lugar de lanzar excepción
            return null;
        }
    }

    // Obtiene el objeto médico completo desde ms-medico
    private Object obtenerMedicoPorId(Long id){
        try {
            return resTem.getForObject(
                "http://ms-medico/medico/buscar/{id}",
                Object.class,
                id
            );
        } catch (HttpClientErrorException | HttpServerErrorException ex){
            // Si falla, retornar null en lugar de lanzar excepción
            return null;
        }
    }
}
