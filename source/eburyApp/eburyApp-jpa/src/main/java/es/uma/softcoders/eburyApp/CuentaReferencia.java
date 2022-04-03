package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CUENTA_REFERENCIA")
public class CuentaReferencia extends Cuenta implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1762735924713432275L;	
	
	// ---------- ATRIBUTOS ----------
	
	// ID es heredado de Cuenta
	
	@Column(name = "NOMBRE_BANCO", nullable = false, length = 20)
	private String 	nombreBanco;
	@Column(length = 20)
	private String 	sucursal;
	@Column(length = 20)
	private String 	pais;
	@Column(scale = 12, precision = 2, nullable = false) 
	private Integer saldo;
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_APERTURA")
	private Date 	fechaApertura;
	@Column(length = 20)
	private String 	estado;
	@OneToOne(mappedBy = "cuentaRef")
	@JoinColumn(name = "SEGREGADA_ID")
	private Segregada segregada;
	@ManyToOne
	@JoinColumn(name = "DIVISA_ID", nullable = false)
	private Divisa divisa;
	
	// Constructor
	public CuentaReferencia() {
		super();
	}
	
	// ------ GETTERS & SETTERS ------

	/**
	 * @return nombre del banco de la cuenta referencia
	 */
	public String getNombreBanco() {
		return nombreBanco;
	}

	/**
	 * @param nombreBanco el nombre del banco de la cuenta referencia
	 */
	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}

	/**
	 * @return la sucursal de la cuenta referencia
	 */
	public String getSucursal() {
		return sucursal;
	}

	/**
	 * @param sucursal la sucursal de la cuenta referencia
	 */
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	/**
	 * @return el país de la cuenta referencia
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * @param pais el país de la cuenta referencia
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * @return el saldo de la cuenta referencia
	 */
	public Integer getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo el saldo de la cuenta referencia
	 */
	public void setSaldo(Integer saldo) {
		this.saldo = saldo;
	}

	/**
	 * @return la fecha de apertura de la cuenta referencia
	 */
	public Date getFechaApertura() {
		return fechaApertura;
	}

	/**
	 * @param fechaApertura la fecha de apertura de la cuenta referencia
	 */
	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	/**
	 * @return el estado de la cuenta referencia
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado el estado de la cuenta referencia
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	// Los métodos equals() y hashCode() se heredan directamente de Cuenta
	
	@Override
	public String toString() {
		return "CuentaReferencia [nombreBanco=" + nombreBanco + ", sucursal=" + sucursal + ", pais=" + pais	//TODO ampliar con id de padre
				+ ", estado=" + estado + "]";
	}
}
