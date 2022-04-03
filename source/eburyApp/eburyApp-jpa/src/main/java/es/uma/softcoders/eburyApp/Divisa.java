package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Divisa implements Serializable{

	public Divisa() {
		
	}
	
	// --------- ATRIBUTOS ---------
	
	@Id
	@Column( length = 3 )
	private String abreviatura;
	
	@Column( nullable = false )
	private String nombre;
	
	private Character simbolo;
	
	@Column( name = "CAMBIO_EURO", nullable = false )
	private Long cambioEuro;
	
	@OneToMany(mappedBy="divisa")
	private CuentaReferencia cuentaReferencia;
	

	// ------ GETTERS & SETTERS ------

	/**
	 * @return the abreviatura
	 */
	public String getAbreviatura() {
		return abreviatura;
	}

	/**
	 * actualiza la abreviatura
	 */
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * actualiza el nombre de la divisa
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the simbolo
	 */
	public Character getSimbolo() {
		return simbolo;
	}

	/**
	 * actualiza el simbolo de la divisa
	 */
	public void setSimbolo(Character simbolo) {
		this.simbolo = simbolo;
	}

	/**
	 * @return the cambioEuro
	 */
	public Long getCambioEuro() {
		return cambioEuro;
	}

	/**
	 * Actualiza el cambio a euro
	 */
	public void setCambioEuro(Long cambioEuro) {
		this.cambioEuro = cambioEuro;
	}


	@Override
	public int hashCode() {
		return Objects.hash(simbolo);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Divisa other = (Divisa) obj;
		return Objects.equals(simbolo, other.simbolo);
	}


	@Override
	public String toString() {
		return "Divisa [abreviatura=" + abreviatura + ", nombre=" + nombre + ", simbolo=" + simbolo + ", cambioEuro="
				+ cambioEuro + "]";
	}

	
	
	
	
	
	
}
