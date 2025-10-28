package clinica.programacionmedica;


import jakarta.persistence.Entity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Transient;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ProgramacionMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long administrativoId;
    private String fechaProgramacion;
    private boolean activo = true; // estado: activo/inactivo
    
    @ElementCollection
    @CollectionTable(name = "programacion_horarios", joinColumns = @JoinColumn(name = "horario_medico_numero"))
    private List<Long> horarioMedicoIds  = new ArrayList<>();


    @Transient
    private List<HorarioMedico> horarios;


    public ProgramacionMedica() {}

    public ProgramacionMedica(Long administrativoId, String fechaProgramacion, boolean activo,
            List<Long> horarioMedicoIds) {
        this.administrativoId = administrativoId;
        this.fechaProgramacion = fechaProgramacion;
        this.activo = activo;
        this.horarioMedicoIds = horarioMedicoIds;
    }
    

    public ProgramacionMedica(Long id, Long administrativoId, String fechaProgramacion, boolean activo,
            List<Long> horarioMedicoIds) {
        this.id = id;
        this.administrativoId = administrativoId;
        this.fechaProgramacion = fechaProgramacion;
        this.activo = activo;
        this.horarioMedicoIds = horarioMedicoIds;
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

    public List<HorarioMedico> getHorarios() {
        return horarios;
    }
    public void setHorarios(List<HorarioMedico> horarios) {
        this.horarios = horarios;
    }

    public List<Long> getHorarioMedicoIds() {
        return horarioMedicoIds;
    }

    public void setHorarioMedicoIds(List<Long> horarioMedicoIds) {
        this.horarioMedicoIds = horarioMedicoIds;
    }

}
