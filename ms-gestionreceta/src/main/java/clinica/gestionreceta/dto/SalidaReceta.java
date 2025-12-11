package clinica.gestionreceta.dto;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"idReceta", "fecha", "estado", "medico", "atencion", "totalDetalles", "detalles"})
public class SalidaReceta {
    private Long idReceta;
    private Object fecha;
    private String estado;
    
    private Map<String, Object> medico;
    private Map<String, Object> atencion;
    private List<Map<String, Object>> detalles;

    public Long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Long idReceta) {
        this.idReceta = idReceta;
    }

    public Object getFecha() {
        return fecha;
    }

    public void setFecha(Object fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Map<String, Object> getMedico() {
        return medico;
    }

    public void setMedico(Map<String, Object> medico) {
        this.medico = medico;
    }

    public Map<String, Object> getAtencion() {
        return atencion;
    }

    public void setAtencion(Map<String, Object> atencion) {
        this.atencion = atencion;
    }



    public List<Map<String, Object>> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Map<String, Object>> detalles) {
        this.detalles = detalles;
    }
}
