package clinica.gestionboleta.dto;

import java.time.LocalDateTime;

public class SalidaBoleta {
    private Long idBoleta;
    private Long idCita;
    private Long idCajero;
    private Double monto;
    private LocalDateTime fecha;
    private String estado;
    
    // Enriquecimiento
    private Object cita; // Datos completos de la cita
    private Object cajero; // Datos del cajero
    private Object paciente; // Datos del paciente

    public Long getIdBoleta() { return idBoleta; }
    public void setIdBoleta(Long idBoleta) { this.idBoleta = idBoleta; }

    public Long getIdCita() { return idCita; }
    public void setIdCita(Long idCita) { this.idCita = idCita; }

    public Long getIdCajero() { return idCajero; }
    public void setIdCajero(Long idCajero) { this.idCajero = idCajero; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Object getCita() { return cita; }
    public void setCita(Object cita) { this.cita = cita; }

    public Object getCajero() { return cajero; }
    public void setCajero(Object cajero) { this.cajero = cajero; }

    public Object getPaciente() { return paciente; }
    public void setPaciente(Object paciente) { this.paciente = paciente; }
}
