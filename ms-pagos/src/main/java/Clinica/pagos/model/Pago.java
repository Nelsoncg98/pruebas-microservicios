package clinica.pagos.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entidad Pago - Representa la transacción de pago procesada por cajero
 * Asociada a una boleta generada por el sistema
 */
@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_transaccion", unique = true)
    private String numeroTransaccion;
    
    // Relación con la boleta
    @Column(name = "boleta_id", nullable = false)
    private Long boletaId;
    
    @Column(name = "numero_boleta", nullable = false)
    private String numeroBoleta;
    
    @Column(name = "monto_pagado", nullable = false)
    private Double montoPagado;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pago", nullable = false)
    private TipoPago tipoPago;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago", nullable = false)
    private EstadoPago estadoPago;
    
    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;
    
    // Información del cajero que procesa
    @Column(name = "cajero_id")
    private Long cajeroId;
    
    @Column(name = "cajero_nombre")
    private String cajeroNombre;
    
    @Column(name = "turno_caja")
    private String turnoCaja;
    
    // Información del paciente (duplicada para consultas rápidas)
    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;
    
    @Column(name = "paciente_dni")
    private String pacienteDni;
    
    @Column(name = "paciente_nombre_completo")
    private String pacienteNombreCompleto;
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    // Para pagos con tarjeta o billetera digital
    @Column(name = "codigo_autorizacion")
    private String codigoAutorizacion;
    
    @Column(name = "ultimos_4_digitos")
    private String ultimos4Digitos;
    
    // Comprobante emitido
    @Column(name = "comprobante_emitido")
    private Boolean comprobanteEmitido = false;
    
    @Column(name = "tipo_comprobante") // BOLETA, FACTURA
    private String tipoComprobante;
    
    @Column(name = "numero_comprobante")
    private String numeroComprobante;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        
        // Estado inicial siempre es PENDIENTE
        if (estadoPago == null) {
            estadoPago = EstadoPago.PENDIENTE;
        }
        
        // Generar número de transacción
        if (numeroTransaccion == null) {
            generarNumeroTransaccion();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    /**
     * Genera un número único de transacción
     */
    private void generarNumeroTransaccion() {
        this.numeroTransaccion = "TXN" + System.currentTimeMillis();
    }
    
    /**
     * Valida que el cajero esté en turno activo
     */
    public boolean cajeroEnTurnoActivo() {
        return cajeroId != null && turnoCaja != null;
    }
    
    /**
     * Valida que el método de pago sea válido
     */
    public boolean metodosPagoValido() {
        return tipoPago != null;
    }
}