package clinica.gestionatencion.service;

import clinica.gestionatencion.client.*;
import clinica.gestionatencion.dto.EntradaAtencion;
import clinica.gestionatencion.dto.SalidaAtencion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GestionAtencionService {

    @Autowired
    private AtencionMedicaClient atencionClient;
    @Autowired
    private CitaClient citaClient;
    @Autowired
    private PacienteClient pacienteClient;
    @Autowired
    private MedicoClient medicoClient;
    @Autowired
    private HistoriaMedicaClient historiaClient;

    public List<SalidaAtencion> listarAtenciones() {
        // 1. Obtener del Core
        List<Map<String, Object>> atenciones = atencionClient.listar();
        
        // 2. Retornar Salida Enriquecida
        return atenciones.stream().map(this::convertirATSalidaAtencion).collect(Collectors.toList());
    }

    private SalidaAtencion convertirATSalidaAtencion(Map<String, Object> atencion) {
        SalidaAtencion salida = new SalidaAtencion();
        salida.setIdAtencionMedica(getLong(atencion, "idAtencionMedica"));
        salida.setCita(citaClient.buscarPorId(getLong(atencion, "idCita")));
        salida.setPaciente(pacienteClient.buscarPorId(getLong(atencion, "idPaciente")));
        salida.setMedico(medicoClient.buscarPorId(getLong(atencion, "idMedico")));
        salida.setHistoriaMedica(historiaClient.buscarPorId(getLong(atencion, "idHistoriaMedica")));
        salida.setDiagnostico((String) atencion.get("diagnostico"));
        salida.setTratamiento((String) atencion.get("tratamiento"));
        salida.setEstado((String) atencion.get("estado"));
        salida.setFechaAtencion(getLocalDateTime(atencion, "fechaAtencion"));
        return salida;
    }

    public SalidaAtencion registrarAtencion(EntradaAtencion entrada) {
        // 1. Validar existencia de IDs (Regla de Negocio: Integridad)
        validarExistencia(entrada);

        // 2. Guardar en Core
        Map<String, Object> atencionGuardada = atencionClient.registrar(entrada);
        
        // 3. Retornar Salida Enriquecida
        Long id = getLong(atencionGuardada, "idAtencionMedica");
        return buscarAtencion(id);
    }

    private void validarExistencia(EntradaAtencion entrada) {
        try {
            if (entrada.getIdCita() != null) citaClient.buscarPorId(entrada.getIdCita());
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar: La Cita con ID " + entrada.getIdCita() + " no existe o no responde.");
        }

        try {
            if (entrada.getIdPaciente() != null) pacienteClient.buscarPorId(entrada.getIdPaciente());
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar: El Paciente con ID " + entrada.getIdPaciente() + " no existe o no responde.");
        }

        try {
            if (entrada.getIdMedico() != null) medicoClient.buscarPorId(entrada.getIdMedico());
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar: El Medico con ID " + entrada.getIdMedico() + " no existe o no responde.");
        }

        try {
            if (entrada.getIdHistoriaMedica() != null) historiaClient.buscarPorId(entrada.getIdHistoriaMedica());
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar: La Historia Medica con ID " + entrada.getIdHistoriaMedica() + " no existe o no responde.");
        }
    }

    public SalidaAtencion buscarAtencion(Long id) {
        // 1. Obtener del Core
        Map<String, Object> atencion = atencionClient.buscarPorId(id);
        if (atencion == null) return null;
        
        // 2. Obtener IDs de manera segura
        Long idCita = getLong(atencion, "idCita");
        Long idPaciente = getLong(atencion, "idPaciente");
        Long idMedico = getLong(atencion, "idMedico");
        Long idHistoria = getLong(atencion, "idHistoriaMedica");
        
        // 3. Obtener Detalles (Enriquecimiento)
        Map<String, Object> cita = null;
        if (idCita != null) {
            try { cita = citaClient.buscarPorId(idCita); } catch (Exception e) { System.err.println("Error buscando Cita: " + e.getMessage()); }
        }
        
        Map<String, Object> paciente = null;
        if (idPaciente != null) {
            try { paciente = pacienteClient.buscarPorId(idPaciente); } catch (Exception e) { System.err.println("Error buscando Paciente: " + e.getMessage()); }
        }
        
        Map<String, Object> medico = null;
        if (idMedico != null) {
            try { medico = medicoClient.buscarPorId(idMedico); } catch (Exception e) { System.err.println("Error buscando Medico: " + e.getMessage()); }
        }
        
        Map<String, Object> historia = null;
        if (idHistoria != null) {
            try { historia = historiaClient.buscarPorId(idHistoria); } catch (Exception e) { System.err.println("Error buscando Historia: " + e.getMessage()); }
        }
        
        // 4. Construir Salida
        SalidaAtencion salida = new SalidaAtencion();
        salida.setIdAtencionMedica(id);
        salida.setCita(cita);
        salida.setPaciente(paciente);
        salida.setMedico(medico);
        salida.setHistoriaMedica(historia);
        
        // Mapear datos propios
        salida.setDiagnostico((String) atencion.get("diagnostico"));
        salida.setTratamiento((String) atencion.get("tratamiento"));
        salida.setEstado((String) atencion.get("estado"));
        
        // Fecha
        String fechaStr = (String) atencion.get("fechaAtencion");
        if (fechaStr != null) {
            try {
                salida.setFechaAtencion(LocalDateTime.parse(fechaStr));
            } catch (Exception e) {
                // Si viene como array o string diferente, manejarlo o dejar null
            }
        }
        
        // Listas vacías por ahora
        salida.setReceta(new ArrayList<>());
        salida.setAnalisisClinico(new ArrayList<>());
        
        return salida;
    }

    private Long getLong(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof Number) {
            return ((Number) val).longValue();
        }
        return null;
    }

    private LocalDateTime getLocalDateTime(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof String) {
            try {
                return LocalDateTime.parse((String) val);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
