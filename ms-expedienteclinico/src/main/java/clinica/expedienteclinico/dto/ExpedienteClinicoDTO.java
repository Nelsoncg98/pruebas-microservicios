package clinica.expedienteclinico.dto;

import java.util.List;

public class ExpedienteClinicoDTO {
    private PacienteDTO paciente;
    private HistoriaMedicaDTO historiaMedica;
     private List<CitaConAtencion> listaCitas;

    public PacienteDTO getPaciente() { return paciente; }
    public void setPaciente(PacienteDTO paciente) { this.paciente = paciente; }
    public HistoriaMedicaDTO getHistoriaMedica() { return historiaMedica; }
    public void setHistoriaMedica(HistoriaMedicaDTO historiaMedica) { this.historiaMedica = historiaMedica; }
    public List<CitaConAtencion> getListaCitas() { return listaCitas; }
    public void setListaCitas(List<CitaConAtencion> listaCitas) { this.listaCitas = listaCitas; }

    public static class CitaConAtencion { // Singular
        private CitaDTO cita;
        private AtencionMedicaDTO atencion; // Ya no es List, es un objeto

        public CitaDTO getCita() { return cita; }
        public void setCita(CitaDTO cita) { this.cita = cita; }
        public AtencionMedicaDTO getAtencion() { return atencion; }
        public void setAtencion(AtencionMedicaDTO atencion) { this.atencion = atencion; }
    }
}
