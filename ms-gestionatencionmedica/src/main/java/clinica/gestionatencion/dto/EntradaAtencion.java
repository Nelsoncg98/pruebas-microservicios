package clinica.gestionatencion.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EntradaAtencion {
    private Long idCita;
    private Long idHistoriaMedica;
    private Long idPaciente;
    private Long idMedico;
    private LocalDateTime fechaAtencion;
    private String diagnostico;
    private String tratamiento;
    private String estado;
}
