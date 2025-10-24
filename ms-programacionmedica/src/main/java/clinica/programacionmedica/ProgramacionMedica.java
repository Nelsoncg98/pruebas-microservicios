package clinica.programacionmedica;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ProgramacionMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long administrativoId;
    private LocalDateTime fechaProgramacion;
    private boolean activo = true; // estado: activo/inactivo
    @Convert(converter = ListLongJsonConverter.class)
    private List<Long> horariosIds = new ArrayList<>();

    public ProgramacionMedica() {}

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

    public LocalDateTime getFechaProgramacion() {
        return fechaProgramacion;
    }

    public void setFechaProgramacion(LocalDateTime fechaProgramacion) {
        this.fechaProgramacion = fechaProgramacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Long> getHorariosIds() {
        return horariosIds;
    }

    public void setHorariosIds(List<Long> horariosIds) {
        this.horariosIds = horariosIds;
    }
}
