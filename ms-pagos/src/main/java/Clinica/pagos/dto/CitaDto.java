package clinica.pagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaDto {
    private Long numero;
    private Long pacienteId;
    private String dniPaciente;
    private Long horarioId;
    private String idDoctor;
    private String motivo;
    private LocalDateTime fecha;
    private String tipoCita;
    private Double costo;
    private String estado;
    private Object horario;
}