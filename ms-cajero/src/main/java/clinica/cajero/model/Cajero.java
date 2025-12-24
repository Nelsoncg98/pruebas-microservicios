package clinica.cajero.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cajeros")
public class Cajero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCajero;
    
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;
    private String codigoEmpleado;

    public Long getIdCajero() { return idCajero; }
    public void setIdCajero(Long idCajero) { this.idCajero = idCajero; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCodigoEmpleado() { return codigoEmpleado; }
    public void setCodigoEmpleado(String codigoEmpleado) { this.codigoEmpleado = codigoEmpleado; }
}
