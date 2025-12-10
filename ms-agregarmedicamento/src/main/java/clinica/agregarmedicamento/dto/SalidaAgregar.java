package clinica.agregarmedicamento.dto;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"idDetalle", "receta", "idMedicamento", "nombreMedicamento", "laboratorio", "precio", "cantidad", "indicaciones"})
public class SalidaAgregar {
    private Long idDetalle;
    private Map<String, Object> receta;
    
    private Long idMedicamento;
    private String nombreMedicamento;
    private String laboratorio;
    private Double precio;
    private Integer cantidad;
    private String indicaciones;

    public Long getIdDetalle() {
        return idDetalle;
    }
    public void setIdDetalle(Long idDetalle) {
        this.idDetalle = idDetalle;
    }
    public Map<String, Object> getReceta() {
        return receta;
    }
    public void setReceta(Map<String, Object> receta) {
        this.receta = receta;
    }
    public Long getIdMedicamento() {
        return idMedicamento;
    }
    public void setIdMedicamento(Long idMedicamento) {
        this.idMedicamento = idMedicamento;
    }
    public String getNombreMedicamento() {
        return nombreMedicamento;
    }
    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }
    public String getLaboratorio() {
        return laboratorio;
    }
    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public String getIndicaciones() {
        return indicaciones;
    }
    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }
}
