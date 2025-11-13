package clinica.historiamedica;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class HistoriaMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistoriaMedica;

    private Long pacienteId; // Id del paciente en ms-paciente (obligatorio)
    private String alergias;
    private String tipoSangre;
    private String enfermedadesCronicas;
    private String antecedentesFamiliares;
    private LocalDate fechaCreacion;

    public HistoriaMedica() {
        this.fechaCreacion = LocalDate.now();
    }

    public HistoriaMedica(Long idHistoriaMedica, Long pacienteId, String alergias,
                          String tipoSangre, String enfermedadesCronicas,
                          String antecedentesFamiliares) {
        this.idHistoriaMedica = idHistoriaMedica;
        this.pacienteId = pacienteId;
        this.alergias = alergias;
        this.tipoSangre = tipoSangre;
        this.enfermedadesCronicas = enfermedadesCronicas;
        this.antecedentesFamiliares = antecedentesFamiliares;
        this.fechaCreacion = LocalDate.now();
    }

    // Getters y setters
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

    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
