package clinica.medico;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import com.fasterxml.jackson.annotation.JsonAlias;

@Entity
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;
    private String nombre;
    private String apellido;
    private String especialidad; // p.ej. "Cardiología"
    private boolean estado = true; // activo/inactivo
    // Nuevo: DNI único del médico dentro del MS
    @Column(unique = true)
    @JsonAlias({"Dni", "dniMedico", "DniMedico"})
    private String dni;
    // Nuevos: datos de contacto
    private String telefono;
    private String email;

    public Medico() {}

    public Medico(Long numero, String nombre, String apellido, String especialidad, boolean estado) {
        this.numero = numero;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
