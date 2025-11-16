package clinica.expedienteclinico.dto;

import java.time.LocalDateTime;

public class CitaDTO {
    private Long numero;
    private Long pacienteId;
    private String dniPaciente;
    private Long horarioId;
    private String idDoctor; // Es String según tu JSON
    private String motivo;
    private LocalDateTime fecha; // Es LocalDateTime, no LocalDate
    private String tipoCita;
    private double costo;
    private String estado;


    // Getters y Setters
    public Long getNumero() { 
        return numero; 
    }
    public void setNumero(Long numero) { 
        this.numero = numero; 
    }
    
    public Long getPacienteId() { 
        return pacienteId; 
    }
    public void setPacienteId(Long pacienteId) { 
        this.pacienteId = pacienteId; 
    }
    
    public String getDniPaciente() { 
        return dniPaciente; 
    }
    public void setDniPaciente(String dniPaciente) { 
        this.dniPaciente = dniPaciente; 
    }
    
    public Long getHorarioId() { 
        return horarioId; 
    }
    public void setHorarioId(Long horarioId) { 
        this.horarioId = horarioId; 
    }
    
    public String getIdDoctor() { 
        return idDoctor; 
    }
    public void setIdDoctor(String idDoctor) { 
        this.idDoctor = idDoctor; 
    }
    
    public String getMotivo() { 
        return motivo; 
    }
    public void setMotivo(String motivo) { 
        this.motivo = motivo; 
    }
    
    public LocalDateTime getFecha() { 
        return fecha; 
    }
    public void setFecha(LocalDateTime fecha) { 
        this.fecha = fecha; 
    }
    
    public String getTipoCita() { 
        return tipoCita; 
    }
    public void setTipoCita(String tipoCita) { 
        this.tipoCita = tipoCita; 
    }
    
    public double getCosto() { 
        return costo; 
    }
    public void setCosto(double costo) { 
        this.costo = costo; 
    }
    
    public String getEstado() { 
        return estado; 
    }
    public void setEstado(String estado) { 
        this.estado = estado; 
    }
}