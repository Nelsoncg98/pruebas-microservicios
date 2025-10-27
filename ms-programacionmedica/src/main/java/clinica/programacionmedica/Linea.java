package clinica.programacionmedica;

import java.time.LocalDate;
import java.time.LocalTime;

public class Linea {
    private Long id;
    private Long numero;

    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Long medicoId; // Id del médico en ms-medico (obligatorio)
    private String consultorio;

    public Linea() {
    }

    public Linea(Long id,Long numero, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Long medicoId,
            String consultorio) {
        this.id = id;
        this.numero = numero;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.medicoId = medicoId;
        this.consultorio = consultorio;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

}
