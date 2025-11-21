package clinica.pagos.model;

public enum TipoPago {
    TARJETA("Tarjeta de crédito/débito"),
    BILLETERA_DIGITAL("Billetera digital (Yape, Plin, etc)"),
    EFECTIVO("Pago en efectivo");
    
    private final String descripcion;
    
    TipoPago(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}