package clinica.nuevaatencion.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class SalidaNuevaAtencion {
    // Datos Enriquecidos
    private Map<String, Object> Cita;
    private Map<String, Object> HistoriaMedica;
    private Map<String, Object> Paciente;
    private Map<String, Object> Medico;
    
    // Campos Clínicos Inicializados (Vacíos)
    private LocalDateTime fechaAtencion; // Puede ser null o fecha actual
    private String diagnostico;
    private String tratamiento;
    private String estado;
    
    private List<String> receta;
    private List<String> analisisClinico;
}
