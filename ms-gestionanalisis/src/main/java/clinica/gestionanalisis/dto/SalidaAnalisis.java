package clinica.gestionanalisis.dto;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"idAnalisis", "fecha", "estado", "medico", "atencion", "totalDetalles", "detalles"})
public class SalidaAnalisis {
    private Long idAnalisis;
    private Object fecha;
    private String estado;
    
    private Map<String, Object> medico;
    private Map<String, Object> atencion;
    private List<Map<String, Object>> detalles;
    
    public Long getIdAnalisis() {
        return idAnalisis;
    }

    public void setIdAnalisis(Long idAnalisis) {
        this.idAnalisis = idAnalisis;
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
