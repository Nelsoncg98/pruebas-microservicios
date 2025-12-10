package clinica.agregarmedicamento.service;

import clinica.agregarmedicamento.client.DetalleRecetaClient;
import clinica.agregarmedicamento.client.MedicamentoClient;
import clinica.agregarmedicamento.client.RecetaClient;
import clinica.agregarmedicamento.dto.EntradaAgregar;
import clinica.agregarmedicamento.dto.SalidaAgregar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AgregarMedicamentoService {

    @Autowired
    private MedicamentoClient medicamentoClient;
    @Autowired
    private RecetaClient recetaClient;
    @Autowired
    private DetalleRecetaClient detalleClient;

    public SalidaAgregar agregar(EntradaAgregar entrada) throws Exception {
        SalidaAgregar salida = new SalidaAgregar();

        // 1. Validar Medicamento (Usa ms-medicamento real)
        Map<String, Object> medicamento = medicamentoClient.buscar(entrada.getIdMedicamento());
        if (medicamento == null) {
            throw new Exception("Medicamento no encontrado con ID: " + entrada.getIdMedicamento());
        }
        
        // 2. Obtener Receta
        Map<String, Object> receta = recetaClient.buscar(entrada.getIdReceta());
        if (receta == null) {
            throw new Exception("Receta no encontrada con ID: " + entrada.getIdReceta());
        }
        salida.setReceta(receta);

        // 3. Guardar Detalle (With Snapshot)
        Map<String, Object> detalleMap = new HashMap<>();
        detalleMap.put("idReceta", entrada.getIdReceta());
        detalleMap.put("idMedicamento", entrada.getIdMedicamento());
        detalleMap.put("cantidad", entrada.getCantidad());
        detalleMap.put("indicaciones", entrada.getIndicaciones());
        
        // Add Snapshot data from Medicamento
        detalleMap.put("nombreMedicamento", medicamento.get("nombre"));
        detalleMap.put("laboratorio", medicamento.get("laboratorio"));
        detalleMap.put("precio", medicamento.get("precio"));

        // Send raw data, let Entity calculate Importe
        Map<String, Object> detalleGuardado = detalleClient.guardar(detalleMap);
        
        // Populate flattened output
        if (detalleGuardado.get("idDetalle") != null)
            salida.setIdDetalle(Long.valueOf(detalleGuardado.get("idDetalle").toString()));
        
        if (detalleGuardado.get("idMedicamento") != null)
             salida.setIdMedicamento(Long.valueOf(detalleGuardado.get("idMedicamento").toString()));
             
        salida.setNombreMedicamento((String) detalleGuardado.get("nombreMedicamento"));
        salida.setLaboratorio((String) detalleGuardado.get("laboratorio"));
        
        if (detalleGuardado.get("precio") != null)
             salida.setPrecio(Double.valueOf(detalleGuardado.get("precio").toString()));
             
        if (detalleGuardado.get("cantidad") != null)
             salida.setCantidad(Integer.valueOf(detalleGuardado.get("cantidad").toString()));
             
        salida.setIndicaciones((String) detalleGuardado.get("indicaciones"));

        return salida;
    }
}
