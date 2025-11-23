package clinica.programacioncompuesta;

import java.util.ArrayList;
import java.util.List;

// Clase simple para reflejar la entidad de ms-programacionmedica
public class ProgramacionMedica {

    private Long id;
    private Long administrativoId;
    private String fechaProgramacion;
    private boolean activo = true;
    private List<Long> horarioMedicoIds = new ArrayList<>();
    private List<HorarioMedico> horarios; // se llena en el compuesto
    
    // Campos enriquecidos para el servicio compuesto
    private Object administrativo; // Objeto completo desde ms-personaladministrativo
    private Object medico; // UN SOLO médico para toda la programación
    private Integer totalHorarios; // Total de horarios en la programación

    public ProgramacionMedica(Long id, Long administrativoId, String fechaProgramacion, boolean activo,
            List<Long> horarioMedicoIds) {
        this.id = id;
        this.administrativoId = administrativoId;
        this.fechaProgramacion = fechaProgramacion;
        this.activo = activo;
        this.horarioMedicoIds = horarioMedicoIds;
    }
    public ProgramacionMedica() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdministrativoId() {
        return administrativoId;
    }
    public void setAdministrativoId(Long administrativoId) {
        this.administrativoId = administrativoId;
    }

    public String getFechaProgramacion() {
        return fechaProgramacion;
    }
    public void setFechaProgramacion(String fechaProgramacion) {
        this.fechaProgramacion = fechaProgramacion;
    }

    public boolean isActivo() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Long> getHorarioMedicoIds() {
        return horarioMedicoIds;
    }
    public void setHorarioMedicoIds(List<Long> horarioMedicoIds) {
        this.horarioMedicoIds = horarioMedicoIds;
    }

    public List<HorarioMedico> getHorarios() {
        return horarios;
    }
    public void setHorarios(List<HorarioMedico> horarios) {
        this.horarios = horarios;
    }

    public Object getAdministrativo() {
        return administrativo;
    }
    public void setAdministrativo(Object administrativo) {
        this.administrativo = administrativo;
    }

    public Object getMedico() {
        return medico;
    }
    public void setMedico(Object medico) {
        this.medico = medico;
    }

    public Integer getTotalHorarios() {
        return totalHorarios;
    }
    public void setTotalHorarios(Integer totalHorarios) {
        this.totalHorarios = totalHorarios;
    }
}