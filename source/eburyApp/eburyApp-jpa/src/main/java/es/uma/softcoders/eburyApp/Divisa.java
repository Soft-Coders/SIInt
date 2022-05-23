package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
public class Divisa implements Serializable{

	private static final long serialVersionUID = -2958945073501018190L;

	public Divisa() {
		super();
	}
	
	public Divisa(String abreviatura, String nombre, Double cambioEuro) {
		
		this.abreviatura = abreviatura;
		this.nombre = nombre;
		this.cambioEuro = cambioEuro;
	}
	public Divisa(String abreviatura, String nombre, Character simbolo, Double cambioEuro) {
		
		this.abreviatura = abreviatura;	
		this.nombre = nombre;
		this.simbolo = simbolo;
		this.cambioEuro = cambioEuro;
	}
	
	// --------- ATRIBUTOS ---------
	
	@Id
	@Column( length = 3 )
	private String abreviatura;
	
	@Column( nullable = false )
	private String nombre;
	
	private Character simbolo;
	
	@Column( name = "CAMBIO_EURO", nullable = false )
	private Double cambioEuro;
	
	// --------- RELACIONES ---------
	
	@OneToMany(mappedBy="divisa")
	private List<CuentaReferencia> cuentaReferencia;
	
	@OneToMany(mappedBy="divisaReceptor")
	private List<Transaccion> transaccionReceptor;
	
	@OneToMany(mappedBy="divisaEmisor")
	private List<Transaccion> transaccionEmisor;

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
	public Double getCambioEuro() {
		return cambioEuro;
	}

	/**
	 * Actualiza el cambio a euro
	 */
	public void setCambioEuro(Double cambioEuro) {
		this.cambioEuro = cambioEuro;
	}


	/**
	 * @return the cuentaReferencia
	 */
	public List<CuentaReferencia> getCuentaReferencia() {
		return cuentaReferencia;
	}

	/**
	 * actualiza las cuentas referencia asociadas a divisa
	 */
	public void setCuentaReferencia(List<CuentaReferencia> cuentaReferencia) {
		this.cuentaReferencia = cuentaReferencia;
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
		return "Divisa = {\n\tabreviatura:" + abreviatura + ", \n\tnombre:" + nombre + ", \n\tsimbolo:" + simbolo + ", \n\tcambioEuro:"
				+ cambioEuro + "\n}";
	}	
}

