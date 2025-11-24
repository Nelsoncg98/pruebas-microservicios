package clinica.expedienteclinico;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import feign.FeignException;

import clinica.expedienteclinico.clients.*;
import clinica.expedienteclinico.dto.*;

@Service
public class ExpedienteClinicoServicio {

    @Autowired
    private ClientePaciente clientePaciente;
    @Autowired
    private ClienteHistoriaMedica clienteHistoria;
    @Autowired
    private ClienteCitas clienteCitas;
    @Autowired
    private ClienteAtencionMedica clienteAtencion;

    public ExpedienteClinicoDTO obtenerExpediente(Long pacienteId) {
        ExpedienteClinicoDTO dto = new ExpedienteClinicoDTO();

        // 1) Paciente
        try {
            PacienteDTO p = clientePaciente.buscarPorId(pacienteId);
            dto.setPaciente(p);
        } catch (FeignException.NotFound nf) {
            dto.setPaciente(null);
        } catch (Exception e) {
            dto.setPaciente(null);
        }

        // 2) Historia médica (si no existe -> null)
        try {
            HistoriaMedicaDTO h = clienteHistoria.buscarPorPacienteId(pacienteId);
            dto.setHistoriaMedica(h);
        } catch (FeignException.NotFound nf) {
            dto.setHistoriaMedica(null);
        } catch (Exception e) {
            dto.setHistoriaMedica(null);
        }

        // 3) Citas
        List<CitaDTO> citas = new ArrayList<>();
        try {
            List<CitaDTO> resp = clienteCitas.listarPorPaciente(pacienteId);
            if (resp != null)
                citas = resp;
        } catch (FeignException.NotFound nf) {
            citas = new ArrayList<>();
        } catch (Exception e) {
            citas = new ArrayList<>();
        }

        // 4) Por cada cita, traer SU atención (singular, no lista)
        List<ExpedienteClinicoDTO.CitaConAtencion> lista = new ArrayList<>();
        for (CitaDTO cita : citas) {
            ExpedienteClinicoDTO.CitaConAtencion item = new ExpedienteClinicoDTO.CitaConAtencion();
            item.setCita(cita);

            AtencionMedicaDTO atencion = null;

            try {
                // 1. Llamamos al cliente que ahora devuelve una LISTA
                List<AtencionMedicaDTO> listaAtenciones = clienteAtencion.obtenerPorCita(cita.getNumero());

                // 2. Verificamos si la lista no es nula y tiene algo
                if (listaAtenciones != null && !listaAtenciones.isEmpty()) {
                    atencion = listaAtenciones.get(0); // Tomamos el primer elemento
                }

            } catch (FeignException.NotFound nf) {
                // Si el microservicio devuelve 404
                atencion = null;
            } catch (Exception e) {
                // Otros errores de conexión
                atencion = null;
                // Opcional: log.error("Error al obtener atencion", e);
            }

            item.setAtencion(atencion);
            lista.add(item);
        }

        dto.setListaCitas(lista);
        return dto;
    }
}
