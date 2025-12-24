package clinica.boleta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "boletas")
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBoleta;

    private Long idCita;
    private Long idCajero;
    private Double monto;
    private LocalDateTime fecha;
    private String estado; // "PENDIENTE", "PAGADO", "ANULADO"
    private String dniPaciente; // Persistencia opcional para tracking

    // Getters and Setters
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

    public String getDniPaciente() { return dniPaciente; }
    public void setDniPaciente(String dniPaciente) { this.dniPaciente = dniPaciente; }
}
