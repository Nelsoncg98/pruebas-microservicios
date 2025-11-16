package clinica.solicitudcita;

import java.util.List;

public class MedicoConHorarios {
	private Long numero;
	private String nombre;
	private String especialidad;
	private List<HorarioMedico> horarios;

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
