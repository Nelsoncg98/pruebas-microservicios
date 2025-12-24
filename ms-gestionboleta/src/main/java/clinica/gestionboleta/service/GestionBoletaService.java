package clinica.gestionboleta.service;

import clinica.gestionboleta.client.*;
import clinica.gestionboleta.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

@Service
public class GestionBoletaService {

    @Autowired
    private BoletaClient boletaClient;
    @Autowired
    private CitaClient citaClient;
    @Autowired
    private CajeroClient cajeroClient;
    @Autowired
    private PacienteClient pacienteClient;

    public SalidaBoleta nuevo(EntradaBoleta entrada) {
        // 1. Validar Cita y Obtener Monto
        Map<String, Object> cita = citaClient.buscar(entrada.getIdCita());
        if (cita == null) {
            throw new RuntimeException("Cita no encontrada: " + entrada.getIdCita());
        }
        
        // Extraer Monto/Costo
        Double monto = 0.0;
        if(cita.get("costo") != null) {
            monto = Double.valueOf(cita.get("costo").toString());
        }
        
        // 2. Validar Cajero
        Map<String, Object> cajero = null;
        if (entrada.getIdCajero() != null) {
             cajero = cajeroClient.buscar(entrada.getIdCajero());
        }

        // 3. Crear Boleta en Core
        Map<String, Object> nuevaBoleta = new HashMap<>();
        nuevaBoleta.put("idCita", entrada.getIdCita());
        nuevaBoleta.put("idCajero", entrada.getIdCajero());
        nuevaBoleta.put("monto", monto);
        nuevaBoleta.put("fecha", LocalDateTime.now().toString()); // Simple format
        nuevaBoleta.put("estado", "PENDIENTE");
        
        // Intentar obtener DNI paciente de la cita
        if(cita.get("pacienteId") != null) {
            try {
                Long idPaciente = Long.valueOf(cita.get("pacienteId").toString());
                Map<String, Object> paciente = pacienteClient.buscar(idPaciente);
                if(paciente != null && paciente.get("dni") != null) {
                    nuevaBoleta.put("dniPaciente", paciente.get("dni").toString());
                }
            } catch (Exception e) {}
        }

        Map<String, Object> boletaCreada = boletaClient.crear(nuevaBoleta);

        // 4. Construir Salida
        return construirSalida(boletaCreada, cita, cajero);
    }

    public SalidaBoleta ver(Long id) {
        Map<String, Object> boleta = boletaClient.buscar(id);
        if (boleta == null) return null;

        Long idCita = Long.valueOf(boleta.get("idCita").toString());
        Long idCajero = boleta.get("idCajero") != null ? Long.valueOf(boleta.get("idCajero").toString()) : null;

        Map<String, Object> cita = citaClient.buscar(idCita);
        Map<String, Object> cajero = (idCajero != null) ? cajeroClient.buscar(idCajero) : null;
        
        return construirSalida(boleta, cita, cajero);
    }
    
    public SalidaBoleta confirmar(Long id) {
        Map<String, Object> boleta = boletaClient.buscar(id);
        if (boleta == null) throw new RuntimeException("Boleta no encontrada");
        
        boleta.put("estado", "PAGADO");
        Map<String, Object> boletaActualizada = boletaClient.actualizar(boleta);
        
        return ver(Long.valueOf(boletaActualizada.get("idBoleta").toString()));
    }

    public void eliminar(Long id) {
        boletaClient.eliminar(id);
    }

    private SalidaBoleta construirSalida(Map<String, Object> boleta, Map<String, Object> cita, Map<String, Object> cajero) {
        SalidaBoleta salida = new SalidaBoleta();
        salida.setIdBoleta(Long.valueOf(boleta.get("idBoleta").toString()));
        salida.setIdCita(Long.valueOf(boleta.get("idCita").toString()));
        if(boleta.get("idCajero") != null) salida.setIdCajero(Long.valueOf(boleta.get("idCajero").toString()));
        if(boleta.get("monto") != null) salida.setMonto(Double.valueOf(boleta.get("monto").toString()));
        salida.setEstado((String) boleta.get("estado"));
        if(boleta.get("fecha") != null) salida.setFecha(LocalDateTime.parse(boleta.get("fecha").toString()));

        salida.setCita(cita);
        salida.setCajero(cajero);
        
        // Enriquecer paciente
        if(cita != null && cita.get("pacienteId") != null) {
            try {
               Long idPaciente = Long.valueOf(cita.get("pacienteId").toString());
               salida.setPaciente(pacienteClient.buscar(idPaciente));
            } catch(Exception e) {}
        }
        
        return salida;
    }
}
