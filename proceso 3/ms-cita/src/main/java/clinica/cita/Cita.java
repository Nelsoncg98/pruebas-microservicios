package clinica.cita;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;

    private Long pacienteId;
    private Long horarioId;
    private String estado = "RESERVADA"; // RESERVADA | CANCELADA

    public Long getNumero() { return numero; }
    public void setNumero(Long numero) { this.numero = numero; }

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public Long getHorarioId() { return horarioId; }
    public void setHorarioId(Long horarioId) { this.horarioId = horarioId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
