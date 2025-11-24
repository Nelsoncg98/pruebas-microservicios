package clinica.atencionmedica.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "atencion_medica")
public class AtencionMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAtencionMedica;

    private Long idCita;
    private Long idHistoriaMedica;
    private Long idPaciente;
    private Long idMedico;
    
    private LocalDateTime fechaAtencion;
    
    private String diagnostico;
    private String tratamiento;
    private String estado;

    // Almacenaremos listas como texto simple por ahora para el Core
    @Column(columnDefinition = "TEXT")
    private String receta; 
    
    @Column(columnDefinition = "TEXT")
    private String analisisClinico;
}
