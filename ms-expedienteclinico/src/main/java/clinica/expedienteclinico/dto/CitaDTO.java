package clinica.expedienteclinico.dto;

public class CitaDTO {
    private Long numero; // tu entidad usa "numero" como id
    private Long pacienteId;
    private Long horarioId;
    private String estado;
    private String fecha;   // opcional (depende de tu ms-citas)
    private String doctor;  // opcional
    private String motivo;  // opcional
    // getters y setters
    public Long getNumero() { return numero; }
    public void setNumero(Long numero) { this.numero = numero; }
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
    public Long getHorarioId() { return horarioId; }
    public void setHorarioId(Long horarioId) { this.horarioId = horarioId; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getDoctor() { return doctor; }
    public void setDoctor(String doctor) { this.doctor = doctor; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
