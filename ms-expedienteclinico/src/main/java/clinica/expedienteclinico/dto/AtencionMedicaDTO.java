package clinica.expedienteclinico.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AtencionMedicaDTO {

    private Long idAtencionMedica;
    private Long idCita;
    private Long idHistoriaMedica;
    private Long idPaciente;
    private Long idMedico;
    private LocalDateTime fechaAtencion;
    private String diagnostico;
    private String tratamiento;
    private String estado;
    
    // Si tienes clases especificas para Receta o Analisis, cámbialo por List<RecetaDTO>
    private List<Object> receta; 
    private List<Object> analisisClinico;

    // --- Getters y Setters ---

    public Long getIdAtencionMedica() {
        return idAtencionMedica;
    }

    public void setIdAtencionMedica(Long idAtencionMedica) {
        this.idAtencionMedica = idAtencionMedica;
    }

    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }

    public Long getIdHistoriaMedica() {
        return idHistoriaMedica;
    }

    public void setIdHistoriaMedica(Long idHistoriaMedica) {
        this.idHistoriaMedica = idHistoriaMedica;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public LocalDateTime getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(LocalDateTime fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Object> getReceta() {
        return receta;
    }

    public void setReceta(List<Object> receta) {
        this.receta = receta;
    }

    public List<Object> getAnalisisClinico() {
        return analisisClinico;
    }

    public void setAnalisisClinico(List<Object> analisisClinico) {
        this.analisisClinico = analisisClinico;
    }
}