package clinica.solicitudcita;

import java.util.List;

public class MedicoConHorarios {
	private Long numero;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String dni;
    private String telefono;
    private String email;
    private Double precio;
	private List<HorarioMedico> horarios;

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public List<HorarioMedico> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<HorarioMedico> horarios) {
		this.horarios = horarios;
	}
}
