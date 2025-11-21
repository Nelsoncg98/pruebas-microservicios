package clinica.pagos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO para mapear la respuesta del microservicio de Citas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaDto {
    
    private Long id;
    private Long pacienteId;
    private String dniPaciente;
    private Long horarioId;
    private Long idDoctor;
    private String motivo;
    private String fecha;
    private String tipoCita;
    private Double costo;
    private String estado;
    private String horario;
}