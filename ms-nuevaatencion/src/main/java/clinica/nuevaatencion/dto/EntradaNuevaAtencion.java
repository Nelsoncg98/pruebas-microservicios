package clinica.nuevaatencion.dto;



public class EntradaNuevaAtencion {
    private Long idCita;
    private Long idHistoriaMedica;
    private Long idMedico;

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

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }
}
