package clinica.nuevaatencion.dto;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SalidaNuevaAtencion {
    // Datos Enriquecidos
    private Map<String, Object> Cita;
    private Map<String, Object> HistoriaMedica;
    private Map<String, Object> Medico;
    
    // Campos Clínicos Inicializados (Vacíos)
    private LocalDateTime fechaAtencion; // Puede ser null o fecha actual
    private String diagnostico;
    private String tratamiento;
    private String estado;
    
    private List<String> receta;
    private List<String> analisisClinico;

    public Map<String, Object> getCita() {
        return Cita;
    }

    public void setCita(Map<String, Object> cita) {
        Cita = cita;
    }

    public Map<String, Object> getHistoriaMedica() {
        return HistoriaMedica;
    }

    public void setHistoriaMedica(Map<String, Object> historiaMedica) {
        HistoriaMedica = historiaMedica;
    }

    public Map<String, Object> getMedico() {
        return Medico;
    }

    public void setMedico(Map<String, Object> medico) {
        Medico = medico;
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
}
