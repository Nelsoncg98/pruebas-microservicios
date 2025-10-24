package clinica.programacionmedica;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class LineaProgramacionDTO {
    private Long numero;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String consultorio;
    private boolean disponible;
    private int duracionMinutos;
    private Long medicoId;
    private String medicoNombre;
    private String especialidad;

    public static LineaProgramacionDTO from(HorarioDTO h, MedicoDTO m){
        LineaProgramacionDTO dto = new LineaProgramacionDTO();
        dto.numero = h.getNumero();
        dto.fecha = h.getFecha();
        dto.horaInicio = h.getHoraInicio();
        dto.horaFin = h.getHoraFin();
        dto.consultorio = h.getConsultorio();
        dto.disponible = h.isDisponible();
        dto.duracionMinutos = (int) Duration.between(h.getHoraInicio(), h.getHoraFin()).toMinutes();
        dto.medicoId = h.getMedicoId();
        if (m != null){
            dto.medicoNombre = (m.getNombre() != null ? m.getNombre() : "") + (m.getApellido()!=null? (" " + m.getApellido()) : "");
            dto.especialidad = m.getEspecialidad();
        }
        return dto;
    }

    public Long getNumero() { return numero; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public String getConsultorio() { return consultorio; }
    public boolean isDisponible() { return disponible; }
    public int getDuracionMinutos() { return duracionMinutos; }
    public Long getMedicoId() { return medicoId; }
    public String getMedicoNombre() { return medicoNombre; }
    public String getEspecialidad() { return especialidad; }
}
