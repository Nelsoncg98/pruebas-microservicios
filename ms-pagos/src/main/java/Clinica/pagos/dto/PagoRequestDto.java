package clinica.pagos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import clinica.pagos.model.EstadoPago;
import clinica.pagos.model.MetodoPago;



public class PagoRequestDto {
   private Long pacienteId;
   private Long citaId;
   public Long getPacienteId() {
      return pacienteId;
   }
   public void setPacienteId(Long pacienteId) {
      this.pacienteId = pacienteId;
   }
   public Long getCitaId() {
      return citaId;
   }
   public void setCitaId(Long citaId) {
      this.citaId = citaId;
   }
   public PagoRequestDto() {
   }
   public PagoRequestDto(Long pacienteId, Long citaId) {
      this.pacienteId = pacienteId;
      this.citaId = citaId;
   }

   
}