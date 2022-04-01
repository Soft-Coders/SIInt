package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Administrador implements Serializable{
	
	public Administrador() {
		
	}
	
	// --------- ATRIBUTOS ---------
	
	@EmbeddedId
	private PrimaryKey id;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private String apellidos;
	
	@Column(name = "FECHA_NACIMIENTO")
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	// ------ GETTERS & SETTERS ------

	/**
	 * @return the id
	 */
	public PrimaryKey getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(PrimaryKey id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos the apellidos to set
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return the fechaNacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Override
	public String toString() {
		return "Administrador [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", fechaNacimiento="
				+ fechaNacimiento + "]";
	}
	

	
	
	
	
}

