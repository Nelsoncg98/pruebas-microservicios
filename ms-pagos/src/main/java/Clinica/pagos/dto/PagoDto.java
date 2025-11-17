package clinica.pagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDto {
    private Long id;
    private Long citaId;
    private Long pacienteId;
    private Double monto;
    private String tipoPago; // TARJETA, BILLETERA_DIGITAL, EFECTIVO
    private String estado; // PENDIENTE, COMPLETADO, RECHAZADO, CANCELADO
    private LocalDateTime fechaPago;
    
    // Información de la cita
    private CitaDto cita;
    
    // Información del paciente
    private PacienteDto paciente;
}