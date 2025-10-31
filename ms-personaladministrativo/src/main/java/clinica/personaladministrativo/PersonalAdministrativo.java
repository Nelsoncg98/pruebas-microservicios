package clinica.personaladministrativo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import com.fasterxml.jackson.annotation.JsonAlias;
import java.time.LocalDate;

@Entity
public class PersonalAdministrativo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero; // Identificador

    private String nombre;
    private String apellido;
    private String telefono;
    private String cargo;
    private LocalDate fecha;
    private String estado;
    
    // Nuevo: DNI único y email para contacto
    @Column(unique = true)
    @JsonAlias({"documento", "Dni", "dniPersonal"})
    private String dni;
    private String email;

    public Long getNumero() { return numero; }
    public void setNumero(Long numero) { this.numero = numero; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
