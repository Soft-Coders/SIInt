package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Usuario implements Serializable{
	
	public Usuario() {
		
	}
	
	// --------- ATRIBUTOS ---------
	
	@Id
	@GeneratedValue
	private Long Id;
	
	@Column(nullable=false, unique=true)
	private String usuario;
	
	@Column(nullable=false, unique=true)
	private String clave;
	
	@Column(name= "ES_ADMINISTRATIVO", nullable=false)
	private boolean esAdministrativo;
	
	@OneToOne(mappedBy="usuario")
	private PersonaAutorizada personaAutorizada;
	
	@OneToOne(mappedBy="INDIVIDUAL_USUARIO")
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
		return "Usuario [Id=" + Id + ", usuario=" + usuario + ", clave=" + clave + ", esAdministrativo="
				+ esAdministrativo + "]";
	}
	
	
	
}
