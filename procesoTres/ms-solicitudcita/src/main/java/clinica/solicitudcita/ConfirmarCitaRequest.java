package clinica.solicitudcita;

/**
 * Clase de entrada para confirmar cita mediante JSON body
 * Permite enviar los datos en el cuerpo de la petición en lugar de query params
 */
public class ConfirmarCitaRequest {
    
    private Integer idPaciente;
    private Integer idDoctor;
    private Integer horarioId;
    private String motivo;
    private String tipoCita;
    private Double costo;

    // Constructor vacío
    public ConfirmarCitaRequest() {
    }

    // Constructor con todos los campos
    public ConfirmarCitaRequest(Integer idPaciente, Integer idDoctor, Integer horarioId, 
                                String motivo, String tipoCita, Double costo) {
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;
        this.horarioId = horarioId;
        this.motivo = motivo;
        this.tipoCita = tipoCita;
        this.costo = costo;
    }

    // Getters y Setters
    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Integer getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(Integer idDoctor) {
        this.idDoctor = idDoctor;
    }

    public Integer getHorarioId() {
        return horarioId;
    }

    public void setHorarioId(Integer horarioId) {
        this.horarioId = horarioId;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(String tipoCita) {
        this.tipoCita = tipoCita;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }
}
