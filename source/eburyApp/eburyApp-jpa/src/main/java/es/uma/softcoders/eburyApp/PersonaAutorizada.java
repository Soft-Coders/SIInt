package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name ="PERSONA_AUTORIZADA")
public class PersonaAutorizada implements Serializable{

	public PersonaAutorizada() {
		
	}
	
	// --------- ATRIBUTOS ---------
	
	@Id
	@Column(nullable = false)
	@GeneratedValue
	private String id;
	
	@Column(nullable = false, unique = true)
	private String identificacion;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private String apellidos;
	
	@Column(nullable = false)
	private String direccion;
	
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	private String estado;
	
	@Column(name = "FECHA_INICIO")
	@Temporal(TemporalType.DATE)
	private Date fechaInicio;
	
	@Column(name = "FECHA_FIN")
	@Temporal(TemporalType.DATE)
	private Date fechaFin;
	
	// --------- RELACIONES ---------
	
	/*
	@ManyToMany(mappedBy = "autorizacion")
	@MapKeyColumn(name = "TIPO", nullable = false)
	private Map<String, Empresa> autorizacion;
	*/
	
	//ElementCollection
    //CollectionTable
    //	JoinColumn (nombreb)
    //MapKeyJoinColumns (nombrea)
    //Column()
	
    @ElementCollection
    @CollectionTable(name="AUTORIZACION",
                     joinColumns=@JoinColumn(name="Empresa"))
    @Column(name="TIPO")
    @MapKeyJoinColumn(name="EMPRESA_FK", referencedColumnName="ID")
    private Map<Empresa, Character> autorizacion;
	
	@OneToOne
	@JoinColumn(name="PERSONA_AUTORIZADA_USUARIO", nullable = false)
	private Usuario usuario;

	// ------ GETTERS & SETTERS ------
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * actualiza el ID del propietario
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the identificacion
	 */
	public String getIdentificacion() {
		return identificacion;
	}

	/**
	 * actualiza la identificacion del propietario
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * actualiza el nombre del propietario
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
	 * actualiza los apellidos del propietario
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * actualiza la dirección del propietario
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the fechaNacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * actualiza la fecha de nacimiento
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * actualiza el estado de la cuenta
	 * @throws Exception 
	 */
	public void setEstado(String estado) throws Exception {
		if(estado.equalsIgnoreCase("ACTIVO")||estado.equalsIgnoreCase("BLOQUEADO")||estado.equalsIgnoreCase("BAJA")) {
			this.estado = estado;
		} else {
			throw new Exception("Estado no válido");
		}
		
	}

	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * actualiza la fecha donde paso a ser persona autorizada
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * actualiza la fecha donde terminó de ser persona autorizada
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonaAutorizada other = (PersonaAutorizada) obj;
		return 	Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "PersonaAutorizada [id=" + id + ", identificacion=" + identificacion + ", nombre=" + nombre
				+ ", apellidos=" + apellidos + ", direccion=" + direccion + ", fechaNacimiento=" + fechaNacimiento
				+ ", estado=" + estado + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + "]";
	}
	
	
	
}
