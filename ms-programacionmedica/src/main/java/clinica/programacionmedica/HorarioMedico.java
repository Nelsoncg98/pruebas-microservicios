package clinica.programacionmedica;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.time.LocalTime;

@Embeddable
public class HorarioMedico {
    
    private Long numero;

    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Long medicoId;       // Id del médico en ms-medico (obligatorio)
    private String consultorio;
    
    // Indica si el horario está disponible. Se define por defecto true y no admite NULL.
    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean disponible = Boolean.TRUE;

    public HorarioMedico() {}

    public HorarioMedico(Long numero, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Long medicoId, String consultorio, Boolean disponible) {
        this.numero = numero;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.medicoId = medicoId;
        this.consultorio = consultorio;
        this.disponible = disponible;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }
    
    public Boolean getDisponible() {
        return disponible;
    }
    
    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    
}

