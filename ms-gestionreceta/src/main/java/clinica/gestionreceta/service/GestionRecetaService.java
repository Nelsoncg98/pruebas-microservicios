package clinica.gestionreceta.service;

import clinica.gestionreceta.client.AtencionClient;
import clinica.gestionreceta.client.DetalleRecetaClient;
import clinica.gestionreceta.client.MedicoClient;
import clinica.gestionreceta.client.RecetaClient;
import clinica.gestionreceta.dto.EntradaReceta;
import clinica.gestionreceta.dto.SalidaReceta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GestionRecetaService {

    @Autowired
    private RecetaClient recetaClient;
    @Autowired
    private DetalleRecetaClient detalleClient;
    @Autowired
    private MedicoClient medicoClient;
    @Autowired
    private AtencionClient atencionClient;

    public SalidaReceta nuevo(EntradaReceta entrada) throws Exception {
        // 1. Validaciones previas
        Map<String, Object> medicoData = medicoClient.buscar(entrada.getIdMedico());
        if (medicoData == null) {
            throw new Exception("Medico no encontrado con ID: " + entrada.getIdMedico());
        }
        
        Map<String, Object> atencionData = atencionClient.buscar(entrada.getIdAtencion());
        if (atencionData == null) {
             throw new Exception("Atencion no encontrada con ID: " + entrada.getIdAtencion());
        }

        // 2. Crear Receta (Cabecera)
        Map<String, Object> bodyReceta = new HashMap<>();
        bodyReceta.put("idAtencion", entrada.getIdAtencion());
        bodyReceta.put("idMedico", entrada.getIdMedico());
        
        Map<String, Object> recetaCreada = recetaClient.crear(bodyReceta);
        
        // 3. Vincular Receta a la Atencion (Actualizar Ms-AtencionMedica)
        try {
            Object idRecetaObj = recetaCreada.get("idReceta");
            atencionClient.actualizarReceta(entrada.getIdAtencion(), String.valueOf(idRecetaObj));
            // Actualizamos el objeto local 'atencionData' para que salga en la respuesta
            atencionData.put("receta", String.valueOf(idRecetaObj));
        } catch (Exception e) {
            System.err.println("No se pudo vincular receta a atencion: " + e.getMessage());
        }

        // 4. Construir Respuesta Enriquecida
        SalidaReceta salida = new SalidaReceta();
        if (recetaCreada.get("idReceta") != null)
            salida.setIdReceta(Long.valueOf(recetaCreada.get("idReceta").toString()));
        salida.setFecha(recetaCreada.get("fecha"));
        salida.setEstado((String) recetaCreada.get("estado"));
        
        salida.setMedico(medicoData);
        salida.setAtencion(atencionData);
        salida.setDetalles(new ArrayList<>());
        salida.setTotalDetalles(0.0);
        
        return salida;
    }

    public SalidaReceta ver(Long id) {
        SalidaReceta salida = new SalidaReceta();
        
        // 1. Obtener Receta Cabecera
        Map<String, Object> receta = recetaClient.buscar(id);
        if (receta != null) {
            if (receta.get("idReceta") != null)
                salida.setIdReceta(Long.valueOf(receta.get("idReceta").toString()));
            salida.setFecha(receta.get("fecha"));
            salida.setEstado((String) receta.get("estado"));
        }
        
        // 2. Enriquecer con Datos Externos (Medico, Atencion)
        if (receta != null) {
            // Medico
            try {
                Object idMedicoObj = receta.get("idMedico");
                if (idMedicoObj != null) {
                    Long idMedico = Long.valueOf(idMedicoObj.toString());
                    salida.setMedico(medicoClient.buscar(idMedico));
                }
            } catch (Exception e) {
               System.err.println("Error buscando medico: " + e.getMessage());
            }

            // Atencion
            try {
                Object idAtencionObj = receta.get("idAtencion");
                if (idAtencionObj != null) {
                    Long idAtencion = Long.valueOf(idAtencionObj.toString());
                    salida.setAtencion(atencionClient.buscar(idAtencion));
                }
            } catch (Exception e) {
                System.err.println("Error buscando atencion: " + e.getMessage());
            }
        }
        
         // 3. Obtener Detalles (Ya vienen con Snapshot)
        List<Map<String, Object>> detalles = detalleClient.listarPorReceta(id);
        if (detalles == null) detalles = new ArrayList<>();
        
        salida.setDetalles(detalles);

        // Calcular Total usando metodos auxiliares (para UML)
        salida.setTotalDetalles(calcularTotal(detalles));
        
        return salida;
    }

    public SalidaReceta finalizar(Long id) throws Exception {
        Map<String, String> bodyUpdate = new HashMap<>();
        bodyUpdate.put("estado", "FINALIZADO");
        
        recetaClient.actualizarEstado(id, bodyUpdate);
        
        return ver(id);
    }

    // Metodos para UML
    private double calcularTotal(List<Map<String, Object>> detalles) {
        double total = 0;
        if (detalles != null) {
            for (Map<String, Object> detalle : detalles) {
                total += calcularImporte(detalle);
            }
        }
        return total;
    }

    private double calcularImporte(Map<String, Object> detalle) {
        if (detalle.get("importe") != null) {
            return Double.parseDouble(detalle.get("importe").toString());
        }
        // Fallback (por si acaso datos viejos)
        double precio = 0;
        int cantidad = 0;
        if (detalle.get("precio") != null) 
            precio = Double.parseDouble(detalle.get("precio").toString());
        if (detalle.get("cantidad") != null) 
            cantidad = Integer.parseInt(detalle.get("cantidad").toString());
        return precio * cantidad;
    }
}
