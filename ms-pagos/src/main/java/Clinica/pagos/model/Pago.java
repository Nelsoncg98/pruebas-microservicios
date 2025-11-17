package clinica.pagos.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Pago {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long pacienteId;
    
    @Column(nullable = false)
    private Long consultaId;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPago metodoPago;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado;
    
    @Column(length = 100)
    private String numeroTransaccion;
    
    @Column(length = 500)
    private String descripcion;
    
    @Column(nullable = false)
    private LocalDateTime fechaPago;
    
    @Column(nullable = false)
    private LocalDateTime fechaRegistro;
    
    @Column
    private LocalDateTime fechaActualizacion;

    public Pago() {
    }

    public Pago(Long consultaId, String descripcion, EstadoPago estado, LocalDateTime fechaActualizacion, LocalDateTime fechaPago, LocalDateTime fechaRegistro, Long id, MetodoPago metodoPago, BigDecimal monto, String numeroTransaccion, Long pacienteId) {
        this.consultaId = consultaId;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaActualizacion = fechaActualizacion;
        this.fechaPago = fechaPago;
        this.fechaRegistro = fechaRegistro;
        this.id = id;
        this.metodoPago = metodoPago;
        this.monto = monto;
        this.numeroTransaccion = numeroTransaccion;
        this.pacienteId = pacienteId;
    }
    
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        if (fechaPago == null) {
            fechaPago = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(Long consultaId) {
        this.consultaId = consultaId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    public String getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(String numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }


}
