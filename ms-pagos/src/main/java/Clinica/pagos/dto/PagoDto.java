package clinica.pagos.dto;

import clinica.pagos.model.EstadoPago;
import clinica.pagos.model.TipoPago;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO para transferir información de Pago
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDto {
    
    private Long id;
    private String numeroTransaccion;
    private Long boletaId;
    private String numeroBoleta;
    private Double montoPagado;
    private TipoPago tipoPago;
    private EstadoPago estadoPago;
    private LocalDateTime fechaPago;
    
    // Información del cajero
    private Long cajeroId;
    private String cajeroNombre;
    private String turnoCaja;
    
    // Información del paciente
    private Long pacienteId;
    private String pacienteDni;
    private String pacienteNombreCompleto;
    
    private String observaciones;
    private String codigoAutorizacion;
    private String ultimos4Digitos;
    
    // Comprobante
    private Boolean comprobanteEmitido;
    private String tipoComprobante;
    private String numeroComprobante;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}