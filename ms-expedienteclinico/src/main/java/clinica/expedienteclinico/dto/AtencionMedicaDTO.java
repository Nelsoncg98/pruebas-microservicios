package clinica.expedienteclinico.dto;

public class AtencionMedicaDTO {
    private Long idAtencion;
    private Long citaId;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;
    private Double peso;
    private Double altura;
    private Integer edad;
    // getters y setters
    public Long getIdAtencion() { return idAtencion; }
    public void setIdAtencion(Long idAtencion) { this.idAtencion = idAtencion; }
    public Long getCitaId() { return citaId; }
    public void setCitaId(Long citaId) { this.citaId = citaId; }
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }
    public Double getAltura() { return altura; }
    public void setAltura(Double altura) { this.altura = altura; }
    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }
}
