package clinica.gestionatencion.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class SalidaAtencion {
    private Long idAtencionMedica;
    private Map<String, Object> Cita;
    private Map<String, Object> HistoriaMedica;
    private Map<String, Object> Paciente;
    private Map<String, Object> Medico;
    
    private LocalDateTime fechaAtencion;
    private String diagnostico;
    private String tratamiento;
    private String estado;
    
    private List<String> receta;
    private List<String> analisisClinico;
}
