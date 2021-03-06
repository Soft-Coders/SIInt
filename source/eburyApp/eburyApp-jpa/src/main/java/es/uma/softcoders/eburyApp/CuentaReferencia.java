package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CUENTA_REFERENCIA")
public class CuentaReferencia extends Cuenta implements Serializable{

	private static final long serialVersionUID = -1762735924713432275L;	
	
	public CuentaReferencia() {
		super();
	}
	
	public CuentaReferencia(String nombreBanco, Double saldo, Divisa divisa) {
		this.nombreBanco = nombreBanco;
		this.saldo       = saldo;
		this.divisa      = divisa;
	}
	
	public CuentaReferencia(String nombreBanco, String sucursal, String pais,
			Double saldo, Date fechaApertura, String estado, Segregada segregada, Divisa divisa) {
		this.nombreBanco   = nombreBanco;
		this.sucursal      = sucursal;
		this.pais          = pais;
		this.saldo         = saldo;
		this.fechaApertura = fechaApertura;
		this.estado        = estado;
		this.segregada     = segregada;
		this.divisa        = divisa;
	}
	// ---------- ATRIBUTOS ----------
	
	// ID es heredado de Cuenta
	
	@Column(name = "NOMBRE_BANCO", nullable = false, length = 40)
	private String 	nombreBanco;
	@Column(length = 20)
	private String 	sucursal;
	@Column(length = 20)
	private String 	pais;
	@Column(scale = 12, precision = 2, nullable = false) 
	private Double saldo;
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_APERTURA")
	private Date 	fechaApertura;
	@Column(length = 20)
	private String 	estado;
	
	// --------- RELACIONES ---------
	
	@OneToOne(mappedBy = "cuentaRef")
	private Segregada segregada;
	
	@ManyToOne
	@JoinColumn(name = "DIVISA_ID", nullable = false)
	private Divisa divisa;
	
    @ElementCollection
    @CollectionTable(name="DEPOSITADA_EN",
                     joinColumns = @JoinColumn(name="CUENTA_REFERENCIA_FK"))
    @Column(name="SALDO")
    @MapKeyJoinColumn(name="POOLED_FK", referencedColumnName="iban")
    private Map<Pooled, Double> depositadaEn;
	
	
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
	 * @return el pa??s de la cuenta referencia
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * @param pais el pa??s de la cuenta referencia
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * @return el saldo de la cuenta referencia
	 */
	public Double getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo el saldo de la cuenta referencia
	 */
	public void setSaldo(Double saldo) {
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
	
	/**
	 * @return the segregada
	 */
	public Segregada getSegregada() {
		return segregada;
	}
	
	/**
	 * @param segregada the segregada to set
	 */
	public void setSegregada(Segregada segregada) {
		this.segregada = segregada;
	}
	
	/**
	 * @return la divisa usada en la cuenta
	 */
	public Divisa getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa la divisa usada en la cuenta
	 */
	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return Map de Pooled Accounts depositadas en esta cuenta con el saldo de esa Pooled Account
	 */
	public Map<Pooled, Double> getDepositadaEn() {
		return depositadaEn;
	}

	/**
	 * @param depositadaEn the depositadaEn to set
	 */
	public void setDepositadaEn(Map<Pooled, Double> depositadaEn) {
		this.depositadaEn = depositadaEn;
	}

	// Los m??todos equals() y hashCode() se heredan directamente de Cuenta

	@Override
	public String toString() {
		return "CuentaReferencia = {\n\t"+ super.toString() +"\n\tnombreBanco: " + nombreBanco + 
				", \n\tsucursal: " + sucursal + ", \n\tpais: " + pais + ", \n\testado: " + estado + "\n}";
	}
}
