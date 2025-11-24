package clinica.gestionatencion.dto;

import java.time.LocalDateTime;

public class EntradaAtencion {

    private Long idCita;
    private Long idHistoriaMedica;
    private Long idPaciente;
    private Long idMedico;
    private LocalDateTime fechaAtencion;
    private String diagnostico;
    private String tratamiento;
    private String estado;

    // --- 1. Constructor Vacío (Obligatorio para Spring/Jackson) ---
    public EntradaAtencion() {
    }

    // --- 2. Constructor con todos los campos (Opcional, pero útil) ---
    public EntradaAtencion(Long idCita, Long idHistoriaMedica, Long idPaciente, Long idMedico, 
                           LocalDateTime fechaAtencion, String diagnostico, String tratamiento, String estado) {
        this.idCita = idCita;
        this.idHistoriaMedica = idHistoriaMedica;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.fechaAtencion = fechaAtencion;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.estado = estado;
    }

    // --- 3. Getters y Setters ---

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

    // --- 4. Método toString (Para ver los datos en los logs) ---
    @Override
    public String toString() {
        return "EntradaAtencion{" +
                "idCita=" + idCita +
                ", idHistoriaMedica=" + idHistoriaMedica +
                ", idPaciente=" + idPaciente +
                ", idMedico=" + idMedico +
                ", fechaAtencion=" + fechaAtencion +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamiento='" + tratamiento + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}