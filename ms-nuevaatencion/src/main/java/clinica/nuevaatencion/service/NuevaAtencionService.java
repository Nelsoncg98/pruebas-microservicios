package clinica.nuevaatencion.service;

import clinica.nuevaatencion.client.*;
import clinica.nuevaatencion.dto.EntradaNuevaAtencion;
import clinica.nuevaatencion.dto.SalidaNuevaAtencion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class NuevaAtencionService {

    @Autowired
    private CitaClient citaClient;
    @Autowired
    private PacienteClient pacienteClient;
    @Autowired
    private MedicoClient medicoClient;
    @Autowired
    private HistoriaMedicaClient historiaClient;

    public SalidaNuevaAtencion prepararAtencion(EntradaNuevaAtencion entrada) {
        SalidaNuevaAtencion salida = new SalidaNuevaAtencion();

        // 1. Obtener Datos Enriquecidos (Si fallan, se lanzará excepción o quedarán null según el cliente)
        if (entrada.getIdCita() != null) {
            try {
                salida.setCita(citaClient.buscarPorId(entrada.getIdCita()));
            } catch (Exception e) {
                // Log error or handle
            }
        }

        if (entrada.getIdPaciente() != null) {
            try {
                salida.setPaciente(pacienteClient.buscarPorId(entrada.getIdPaciente()));
            } catch (Exception e) {
                // Log error
            }
        }

        if (entrada.getIdMedico() != null) {
            try {
                salida.setMedico(medicoClient.buscarPorId(entrada.getIdMedico()));
            } catch (Exception e) {
                // Log error
            }
        }

        if (entrada.getIdHistoriaMedica() != null) {
            try {
                salida.setHistoriaMedica(historiaClient.buscarPorId(entrada.getIdHistoriaMedica()));
            } catch (Exception e) {
                // Log error
            }
        }

        // 2. Inicializar Campos Clínicos Vacíos (Requerimiento Clave)
        salida.setDiagnostico("");
        salida.setTratamiento("");
        salida.setEstado(""); // O "PENDIENTE" si se prefiere un valor por defecto
        salida.setReceta(new ArrayList<>());
        salida.setAnalisisClinico(new ArrayList<>());
        
        // Fecha puede ser null o la actual
        // salida.setFechaAtencion(LocalDateTime.now()); 

        return salida;
    }
}
