package clinica.pagos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO para mapear la respuesta del microservicio de Pacientes
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDto {
    
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String fechaNacimiento;
    private String telefono;
    private String email;
    private String direccion;
    private String estado;
}