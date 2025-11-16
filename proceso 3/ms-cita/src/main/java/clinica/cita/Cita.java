package clinica.cita;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.time.LocalDateTime;

@Entity
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;

    // paciente dni o id
    private Long pacienteId;
    private String dniPaciente;

    private Long horarioId; // 
    private String idDoctor; // identificador del médico asignado
    private String motivo; // motivo breve de la cita
    private LocalDateTime fecha; // fecha y hora efectivas de la cita
    private String tipoCita; // CONSULTA | CONTROL | EMERGENCIA | TELECONSULTA (libre por ahora)
    private double costo; // costo de la cita
    private String estado = "RESERVADA"; // RESERVADA | CANCELADA | FINALIZADA

    // horario enriquecido
    @Transient
    private HorarioMedico horario;

    public Cita(Long numero, Long pacienteId, String dniPaciente, Long horarioId, String idDoctor, String motivo,
            LocalDateTime fecha, String tipoCita, double costo, String estado) {
        this.numero = numero;
        this.pacienteId = pacienteId;
        this.dniPaciente = dniPaciente;
        this.horarioId = horarioId;
        this.idDoctor = idDoctor;
        this.motivo = motivo;
        this.fecha = fecha;
        this.tipoCita = tipoCita;
        this.costo = costo;
        this.estado = estado;
    }

    public Cita(Long numero, Long pacienteId, String dniPaciente, Long horarioId, String idDoctor, String motivo,
            LocalDateTime fecha, String tipoCita, String estado, HorarioMedico horario) {
        this.numero = numero;
        this.pacienteId = pacienteId;
        this.dniPaciente = dniPaciente;
        this.horarioId = horarioId;
        this.idDoctor = idDoctor;
        this.motivo = motivo;
        this.fecha = fecha;
        this.tipoCita = tipoCita;
        this.estado = estado;
        this.horario = horario;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }

    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public HorarioMedico getHorario() {
        return horario;
    }

    public void setHorario(HorarioMedico horario) {
        this.horario = horario;
    }

    public Cita() {
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getHorarioId() {
        return horarioId;
    }

    public void setHorarioId(Long horarioId) {
        this.horarioId = horarioId;
    }

    public String getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(String idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(String tipoCita) {
        this.tipoCita = tipoCita;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
}
