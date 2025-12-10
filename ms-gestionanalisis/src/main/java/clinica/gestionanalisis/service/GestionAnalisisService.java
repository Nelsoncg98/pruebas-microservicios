package clinica.gestionanalisis.service;

import clinica.gestionanalisis.client.AnalisisClient;
import clinica.gestionanalisis.client.AtencionClient;
import clinica.gestionanalisis.client.DetalleAnalisisClient;
import clinica.gestionanalisis.client.MedicoClient;
import clinica.gestionanalisis.dto.EntradaAnalisis;
import clinica.gestionanalisis.dto.SalidaAnalisis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GestionAnalisisService {

    @Autowired
    private AnalisisClient analisisClient;
    @Autowired
    private DetalleAnalisisClient detalleClient;
    @Autowired
    private MedicoClient medicoClient;
    @Autowired
    private AtencionClient atencionClient;

    public SalidaAnalisis nuevo(EntradaAnalisis entrada) throws Exception {
        // 1. Validaciones
        Map<String, Object> medicoData = medicoClient.buscar(entrada.getIdMedico());
        Map<String, Object> atencionData = atencionClient.buscar(entrada.getIdAtencion());
        
        if (medicoData == null) throw new Exception("Medico no encontrado");
        if (atencionData == null) throw new Exception("Atencion no encontrada");

        // 2. Crear Analisis
        Map<String, Object> analisisMap = new HashMap<>();
        analisisMap.put("idAtencion", entrada.getIdAtencion());
        analisisMap.put("idMedico", entrada.getIdMedico());
        
        Map<String, Object> creado = analisisClient.crear(analisisMap);
        
        // 3. Vincular Analisis a Atencion
        try {
            Object idAnalisisObj = creado.get("idAnalisis");
            atencionClient.actualizarAnalisis(entrada.getIdAtencion(), String.valueOf(idAnalisisObj));
            atencionData.put("analisisClinico", String.valueOf(idAnalisisObj));
        } catch (Exception e) {
            System.err.println("Error vinculando analisis: " + e.getMessage());
        }

        // 4. Respuesta
        SalidaAnalisis salida = new SalidaAnalisis();
        if (creado.get("idAnalisis") != null)
            salida.setIdAnalisis(Long.valueOf(creado.get("idAnalisis").toString()));
        salida.setFecha(creado.get("fecha"));
        salida.setEstado((String) creado.get("estado"));
        
        salida.setMedico(medicoData);
        salida.setAtencion(atencionData);
        salida.setTotalDetalles(0.0);
        salida.setDetalles(new ArrayList<>());
        
        return salida;
    }

    public SalidaAnalisis ver(Long id) {
        SalidaAnalisis salida = new SalidaAnalisis();
        
        // 1. Cabecera (Ms-Analisis)
        Map<String, Object> analisis = analisisClient.buscar(id);
        if (analisis != null) {
            if (analisis.get("idAnalisis") != null)
                salida.setIdAnalisis(Long.valueOf(analisis.get("idAnalisis").toString()));
            salida.setFecha(analisis.get("fecha"));
            salida.setEstado((String) analisis.get("estado"));
        }
        
        // 2. Enriquecer Cabecera
        if (analisis != null) {
            try {
                Long idMed = Long.valueOf(analisis.get("idMedico").toString());
                salida.setMedico(medicoClient.buscar(idMed));
            } catch (Exception e) {
                // Log and continue
            }
            try {
                Long idAt = Long.valueOf(analisis.get("idAtencion").toString());
                salida.setAtencion(atencionClient.buscar(idAt));
            } catch (Exception e) {
                // Log and continue
            }
        }

        // 3. Detalles (Ms-DetalleAnalisis) - YA VIENE CON SNAPSHOT
        List<Map<String, Object>> detalles = detalleClient.listarPorAnalisis(id);
        if (detalles == null) detalles = new ArrayList<>();
        salida.setDetalles(detalles);
        
        // Calcular Total usando metodos auxiliares (para UML)
        salida.setTotalDetalles(calcularTotal(detalles));

        return salida;
    }

    public SalidaAnalisis finalizar(Long id) {
        analisisClient.actualizarEstado(id, "FINALIZADO");
        return ver(id); 
    }

    // Metodos para UML (Ahora suma el 'importe' persistido)
    private double calcularTotal(List<Map<String, Object>> detalles) {
        double total = 0;
        if (detalles != null) {
            for (Map<String, Object> detalle : detalles) {
                if (detalle.get("costoTipo") != null) {
                    total += Double.parseDouble(detalle.get("costoTipo").toString());
                }
            }
        }
        return total;
    }
}
