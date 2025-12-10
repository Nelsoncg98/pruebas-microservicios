package clinica.detalleanalisis.model;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_analisis")
public class DetalleAnalisis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    private Long idAnalisis;
    private Long idTipo;
    
    // SNAPSHOT FIELDS
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

    public Long getIdAnalisis() {
        return idAnalisis;
    }

    public void setIdAnalisis(Long idAnalisis) {
        this.idAnalisis = idAnalisis;
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
