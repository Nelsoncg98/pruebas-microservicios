package clinica.pagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDto {
    private Long numero;
    private String nombre;
    private String apellido;
    private String dni;
    private String fechaNacimiento;
    private String telefono;
    private String email;
    private String direccion;
    private Boolean estado;
}