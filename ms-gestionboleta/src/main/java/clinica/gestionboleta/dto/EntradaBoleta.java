package clinica.gestionboleta.dto;

import java.time.LocalDateTime;

public class EntradaBoleta {
    private Long idCita;
    private Long idCajero;
    // Opcionales o para testing directo
    private Double monto;
    private String dniPaciente;

    public Long getIdCita() { return idCita; }
    public void setIdCita(Long idCita) { this.idCita = idCita; }

    public Long getIdCajero() { return idCajero; }
    public void setIdCajero(Long idCajero) { this.idCajero = idCajero; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public String getDniPaciente() { return dniPaciente; }
    public void setDniPaciente(String dniPaciente) { this.dniPaciente = dniPaciente; }
}
