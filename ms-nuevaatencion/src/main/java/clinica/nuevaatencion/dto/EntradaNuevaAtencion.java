package clinica.nuevaatencion.dto;

import lombok.Data;

@Data
public class EntradaNuevaAtencion {
    private Long idCita;
    private Long idHistoriaMedica;
    private Long idPaciente;
    private Long idMedico;
}
