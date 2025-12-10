package clinica.agregartipo.dto;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"idDetalle", "analisis", "idTipo", "nombreTipo", "descripcionTipo", "costoTipo", "laboratorioTipo", "indicaciones"})
public class SalidaAgregar {
    private Long idDetalle;
    private Map<String, Object> analisis;
    
    private Long idTipo;
    private String nombreTipo;
    private String descripcionTipo;
    private Double costoTipo;
    private String laboratorioTipo;
    private String indicaciones;

    public Long getIdDetalle() {
        return idDetalle;
    }
    public void setIdDetalle(Long idDetalle) {
        this.idDetalle = idDetalle;
    }
    public Map<String, Object> getAnalisis() {
        return analisis;
    }
    public void setAnalisis(Map<String, Object> analisis) {
        this.analisis = analisis;
    }
    public Long getIdTipo() {
        return idTipo;
    }
    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }
    public String getNombreTipo() {
        return nombreTipo;
    }
    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }
    public String getDescripcionTipo() {
        return descripcionTipo;
    }
    public void setDescripcionTipo(String descripcionTipo) {
        this.descripcionTipo = descripcionTipo;
    }
    public Double getCostoTipo() {
        return costoTipo;
    }
    public void setCostoTipo(Double costoTipo) {
        this.costoTipo = costoTipo;
    }
    public String getLaboratorioTipo() {
        return laboratorioTipo;
    }
    public void setLaboratorioTipo(String laboratorioTipo) {
        this.laboratorioTipo = laboratorioTipo;
    }
    public String getIndicaciones() {
        return indicaciones;
    }
    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }
}
