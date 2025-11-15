package clinica.expedienteclinico.dto;

public class HistoriaMedicaDTO {
    private Long idHistoriaMedica;
    private Long pacienteId;
    private String alergias;
    private String tipoSangre;
    private String enfermedadesCronicas;
    private String antecedentesFamiliares;
    private String fechaCreacion;
    // getters y setters
    public Long getIdHistoriaMedica() { return idHistoriaMedica; }
    public void setIdHistoriaMedica(Long idHistoriaMedica) { this.idHistoriaMedica = idHistoriaMedica; }
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
    public String getAlergias() { return alergias; }
    public void setAlergias(String alergias) { this.alergias = alergias; }
    public String getTipoSangre() { return tipoSangre; }
    public void setTipoSangre(String tipoSangre) { this.tipoSangre = tipoSangre; }
    public String getEnfermedadesCronicas() { return enfermedadesCronicas; }
    public void setEnfermedadesCronicas(String enfermedadesCronicas) { this.enfermedadesCronicas = enfermedadesCronicas; }
    public String getAntecedentesFamiliares() { return antecedentesFamiliares; }
    public void setAntecedentesFamiliares(String antecedentesFamiliares) { this.antecedentesFamiliares = antecedentesFamiliares; }
    public String getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(String fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
