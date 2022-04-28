package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = -1977601704778764068L;

	public Usuario() {
		super();
	}
	
	public Usuario(String usuario, String clave) throws NullPointerException{
		
		this.usuario = usuario;
		this.clave = clave;
	}
	
	// --------- ATRIBUTOS ---------
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	@Column(nullable=false, unique=true)
	private String usuario;
	
	@Column(nullable=false, unique=true)
	private String clave;
	
	// --------- RELACIONES ---------
	
	@Column(name= "ES_ADMINISTRATIVO", nullable=false)
	private boolean esAdministrativo;
	
	@OneToOne(mappedBy="usuario", cascade = CascadeType.PERSIST)
	private PersonaAutorizada personaAutorizada;
	
	@OneToOne(mappedBy="usuario", cascade = CascadeType.PERSIST)
	private Individual individual;
	
	// ------ GETTERS & SETTERS ------

	/**
	 * @return the id
	 */
	public Long getId() {
		return Id;
	}

	/**
	 * actualiza el ID
	 */
	public void setId(Long id) {
		Id = id;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * actualiza el nombre de usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * actualiza la clave
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * @return the esAdministrativo
	 */
	public boolean isEsAdministrativo() {
		return esAdministrativo;
	}

	/**
	 * actualiza es_Administrativo
	 */
	public void setEsAdministrativo(boolean esAdministrativo) {
		this.esAdministrativo = esAdministrativo;
	}

	/**
	 * @return the personaAutorizada
	 */
	public PersonaAutorizada getPersonaAutorizada() {
		return personaAutorizada;
	}

	/**
	 * actualiza la persona autorizada asociada al usuario
	 */
	public void setPersonaAutorizada(PersonaAutorizada personaAutorizada) {
		this.personaAutorizada = personaAutorizada;
	}

	/**
	 * @return the individual
	 */
	public Individual getIndividual() {
		return individual;
	}

	/**
	 * actualiza la cuenta individual asociada a usuario
	 */
	public void setIndividual(Individual individual) {
		this.individual = individual;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(Id, other.Id);
	}

	@Override
	public String toString() {
		return "Usuario = {\n\tId: " + Id + ", \n\tusuario: " + usuario + ", \n\tclave: " + clave + 
				", \n\tesAdministrativo: " + esAdministrativo + "\n}";
	}
	
}
