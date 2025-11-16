package clinica.solicitudcita;

import java.time.LocalDateTime;

// Representa un horario médico tal como lo devuelve ms-horariomedico
public class HorarioMedico {
    private Integer idHorarioMedico;
    private Integer idMedico;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private String estado; // LIBRE, RESERVADO, etc.

    public Integer getIdHorarioMedico() {
        return idHorarioMedico;
    }

    public void setIdHorarioMedico(Integer idHorarioMedico) {
        this.idHorarioMedico = idHorarioMedico;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
