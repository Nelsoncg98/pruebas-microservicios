package clinica.agregartipo.service;

import clinica.agregartipo.client.AnalisisClient;
import clinica.agregartipo.client.DetalleAnalisisClient;
import clinica.agregartipo.client.TipoAnalisisClient;
import clinica.agregartipo.dto.EntradaAgregar;
import clinica.agregartipo.dto.SalidaAgregar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AgregarTipoService {

    @Autowired
    private AnalisisClient analisisClient;
    @Autowired
    private TipoAnalisisClient tipoAnalisisClient;
    @Autowired
    private DetalleAnalisisClient detalleClient;

    public SalidaAgregar agregar(EntradaAgregar entrada) throws Exception {
        SalidaAgregar salida = new SalidaAgregar();

        // 1. Validar Tipo Analisis (Catalogo Real)
        Map<String, Object> tipo = tipoAnalisisClient.buscar(entrada.getIdTipo());
        if (tipo == null) {
            throw new Exception("Tipo Analisis no encontrado con ID: " + entrada.getIdTipo());
        }

        // 2. Obtener Analisis (Maestro)
        Map<String, Object> analisis = analisisClient.buscar(entrada.getIdAnalisis());
        if (analisis == null) {
            throw new Exception("Analisis no encontrado con ID: " + entrada.getIdAnalisis());
        }
        salida.setAnalisis(analisis);

        // 3. Preparar Detalle con Snapshot (Write-Time Enrichment)
        Map<String, Object> detalleMap = new HashMap<>();
        detalleMap.put("idAnalisis", entrada.getIdAnalisis());
        detalleMap.put("idTipo", entrada.getIdTipo());
        detalleMap.put("indicaciones", entrada.getIndicaciones());
        
        // SNAPSHOT: Copiar datos del Tipo al Detalle
        detalleMap.put("nombreTipo", tipo.get("nombre"));
        detalleMap.put("descripcionTipo", tipo.get("descripcion"));
        detalleMap.put("costoTipo", tipo.get("costo"));
        detalleMap.put("laboratorioTipo", tipo.get("laboratorio"));



        // 4. Guardar
        Map<String, Object> detalleGuardado = detalleClient.guardar(detalleMap);
        
        // Populate flattened output
        if (detalleGuardado.get("idDetalle") != null)
            salida.setIdDetalle(Long.valueOf(detalleGuardado.get("idDetalle").toString()));
            
        if (detalleGuardado.get("idTipo") != null)
            salida.setIdTipo(Long.valueOf(detalleGuardado.get("idTipo").toString()));
            
        salida.setNombreTipo((String) detalleGuardado.get("nombreTipo"));
        salida.setDescripcionTipo((String) detalleGuardado.get("descripcionTipo"));
        
        if (detalleGuardado.get("costoTipo") != null)
            salida.setCostoTipo(Double.valueOf(detalleGuardado.get("costoTipo").toString()));
            
        salida.setLaboratorioTipo((String) detalleGuardado.get("laboratorioTipo"));
        salida.setIndicaciones((String) detalleGuardado.get("indicaciones"));

        return salida;
    }
}
