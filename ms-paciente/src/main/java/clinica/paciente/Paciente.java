package clinica.paciente;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonAlias;
import java.time.LocalDate;

@Entity
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;

    private String nombre;
    private String apellido;
    // DNI canónico (único). Se aceptan alias de entrada por compatibilidad
    @Column(unique = true)
    @JsonAlias({"documento", "DniPaciente", "dniPaciente"})
    private String dni;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String email;
    // Nuevo: dirección postal
    private String direccion;
    private boolean estado = true;

    public Long getNumero() { return numero; }
    public void setNumero(Long numero) { this.numero = numero; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
