package clinica.gestionatencion.dto;

import java.time.LocalDateTime;

public class EntradaAtencion {

    private Long idCita;
    private Long idHistoriaMedica;
    private Long idMedico;
    private LocalDateTime fechaAtencion;
    private String diagnostico;
    private String tratamiento;
    private String estado;

    public EntradaAtencion() {
    }

    public EntradaAtencion(Long idCita, Long idHistoriaMedica, Long idMedico, 
                           LocalDateTime fechaAtencion, String diagnostico, String tratamiento, String estado) {
        this.idCita = idCita;
        this.idHistoriaMedica = idHistoriaMedica;
        this.idMedico = idMedico;
        this.fechaAtencion = fechaAtencion;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.estado = estado;
    }

    public Long getIdCita() { return idCita; }
    public void setIdCita(Long idCita) { this.idCita = idCita; }

    public Long getIdHistoriaMedica() { return idHistoriaMedica; }
    public void setIdHistoriaMedica(Long idHistoriaMedica) { this.idHistoriaMedica = idHistoriaMedica; }

    public Long getIdMedico() { return idMedico; }
    public void setIdMedico(Long idMedico) { this.idMedico = idMedico; }

    public LocalDateTime getFechaAtencion() { return fechaAtencion; }
    public void setFechaAtencion(LocalDateTime fechaAtencion) { this.fechaAtencion = fechaAtencion; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "EntradaAtencion{" +
                "idCita=" + idCita +
                ", idHistoriaMedica=" + idHistoriaMedica +
                ", idMedico=" + idMedico +
                ", fechaAtencion=" + fechaAtencion +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamiento='" + tratamiento + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}