package clinica.pagos.model;

public enum EstadoPago {
    PENDIENTE("Boleta generada, pago no realizado"),
    PROCESANDO("Cajero validando el pago"),
    COMPLETADO("Pago confirmado exitosamente"),
    RECHAZADO("Pago no procesado correctamente"),
    ANULADO("Pago cancelado por el sistema");
    
    private final String descripcion;
    
    EstadoPago(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}
