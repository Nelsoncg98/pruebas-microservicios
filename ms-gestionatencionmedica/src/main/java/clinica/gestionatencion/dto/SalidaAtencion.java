package clinica.gestionatencion.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SalidaAtencion {

    private Long idAtencionMedica;
    private Map<String, Object> Cita;
    private Map<String, Object> HistoriaMedica;
    private Map<String, Object> Paciente;
    private Map<String, Object> Medico;
    
    private LocalDateTime fechaAtencion;
    private String diagnostico;
    private String tratamiento;
    private String estado;
    
    private List<String> receta;
    private List<String> analisisClinico;

    // --- 1. Constructor Vacío (Obligatorio) ---
    public SalidaAtencion() {
    }

    // --- 2. Constructor Completo ---
    public SalidaAtencion(Long idAtencionMedica, Map<String, Object> Cita, Map<String, Object> HistoriaMedica, 
                          Map<String, Object> Paciente, Map<String, Object> Medico, LocalDateTime fechaAtencion, 
                          String diagnostico, String tratamiento, String estado, List<String> receta, 
                          List<String> analisisClinico) {
        this.idAtencionMedica = idAtencionMedica;
        this.Cita = Cita;
        this.HistoriaMedica = HistoriaMedica;
        this.Paciente = Paciente;
        this.Medico = Medico;
        this.fechaAtencion = fechaAtencion;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.estado = estado;
        this.receta = receta;
        this.analisisClinico = analisisClinico;
    }

    // --- 3. Getters y Setters ---

    public Long getIdAtencionMedica() {
        return idAtencionMedica;
    }

    public void setIdAtencionMedica(Long idAtencionMedica) {
        this.idAtencionMedica = idAtencionMedica;
    }

    public Map<String, Object> getCita() {
        return Cita;
    }

    public void setCita(Map<String, Object> Cita) {
        this.Cita = Cita;
    }

    public Map<String, Object> getHistoriaMedica() {
        return HistoriaMedica;
    }

    public void setHistoriaMedica(Map<String, Object> HistoriaMedica) {
        this.HistoriaMedica = HistoriaMedica;
    }

    public Map<String, Object> getPaciente() {
        return Paciente;
    }

    public void setPaciente(Map<String, Object> Paciente) {
        this.Paciente = Paciente;
    }

    public Map<String, Object> getMedico() {
        return Medico;
    }

    public void setMedico(Map<String, Object> Medico) {
        this.Medico = Medico;
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

    public List<String> getReceta() {
        return receta;
    }

    public void setReceta(List<String> receta) {
        this.receta = receta;
    }

    public List<String> getAnalisisClinico() {
        return analisisClinico;
    }

    public void setAnalisisClinico(List<String> analisisClinico) {
        this.analisisClinico = analisisClinico;
    }

    // --- 4. toString ---
    @Override
    public String toString() {
        return "SalidaAtencion{" +
                "idAtencionMedica=" + idAtencionMedica +
                ", Cita=" + Cita +
                ", HistoriaMedica=" + HistoriaMedica +
                ", Paciente=" + Paciente +
                ", Medico=" + Medico +
                ", fechaAtencion=" + fechaAtencion +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamiento='" + tratamiento + '\'' +
                ", estado='" + estado + '\'' +
                ", receta=" + receta +
                ", analisisClinico=" + analisisClinico +
                '}';
    }
}