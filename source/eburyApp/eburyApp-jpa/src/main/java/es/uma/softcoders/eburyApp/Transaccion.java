package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Transaccion implements Serializable{

	private static final long serialVersionUID = 6259768208959563654L;
	
	public Transaccion() {
		
	}
	
	// ----------- ATRIBUTOS -----------
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_INSTRUCCION", nullable = false)
	private Date fechaInstruccion;
	
	@Column(nullable = false)
	private Integer cantidad;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_EJECUCION")
	private Date fechaEjecucion;
	
	@Column(nullable = false)
	private String tipo;
	
	// En ABD se ha considerado como la cantidad completa, no el porcentaje. LO CAMBIAMOS?
	private Integer comision;
	
	private String internacional;
	
	// ---------- RELACIONES -----------
	
	@ManyToOne
	@JoinColumn(name="DIVISA_RECEPTOR_FK", nullable=false)
	@Column(name = "DIVISA_RECEPTOR")
	private Divisa divisaReceptor;
	
	@ManyToOne
	@JoinColumn(name="DIVISA_EMISOR_FK", nullable=false)
	@Column(name = "DIVISA_EMISOR")
	private Divisa divisaEmisor;
	
	@ManyToOne
	@JoinColumn(name = "ORIGEN_FK", nullable = false)
	private Cuenta origen;
	

	@ManyToOne
	@JoinColumn(name = "DESTINO_FK", nullable = false)
	private Cuenta destino;

	// ------ GETTERS & SETTERS ------ 	
	
	/**
	 * @return id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id el id
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return la fecha de instrucción
	 */
	public Date getFechaInstruccion() {
		return fechaInstruccion;
	}


	/**
	 * @param fechaInstruccion la fecha de instrucción
	 */
	public void setFechaInstruccion(Date fechaInstruccion) {
		this.fechaInstruccion = fechaInstruccion;
	}


	/**
	 * @return la cantidad de la transacción
	 */
	public Integer getCantidad() {
		return cantidad;
	}


	/**
	 * @param cantidad la cantidad de la transacción
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}


	/**
	 * @return la fecha de ejecución
	 */
	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}


	/**
	 * @param fechaEjecucion la fecha de ejecución
	 */
	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}


	/**
	 * @return el tipo de la transacción
	 */
	public String getTipo() {
		return tipo;
	}


	/**
	 * @param tipo el tipo de la transacción
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	/**
	 * @return la comisión de Ebury en la transacción
	 */
	public Integer getComision() {
		return comision;
	}


	/**
	 * @param comision la comisión de Ebury en la transacción
	 */
	public void setComision(Integer comision) {
		this.comision = comision;
	}


	/**
	 * @return internacional TODO
	 */
	public String getInternacional() {
		return internacional;
	}


	/**
	 * @param internacional TODO
	 */
	public void setInternacional(String internacional) {
		this.internacional = internacional;
	}


	/**
	 * @return la divisa de la cuenta receptora
	 */
	public Divisa getDivisaReceptor() {
		return divisaReceptor;
	}


	/**
	 * @param divisaReceptor la divisa de la cuenta receptora
	 */
	public void setDivisaReceptor(Divisa divisaReceptor) {
		this.divisaReceptor = divisaReceptor;
	}


	/**
	 * @return la divisa de la cuenta emisora
	 */
	public Divisa getDivisaEmisor() {
		return divisaEmisor;
	}


	/**
	 * @param divisaEmisor la divisa de la cuenta emisora
	 */
	public void setDivisaEmisor(Divisa divisaEmisor) {
		this.divisaEmisor = divisaEmisor;
	}


	/**
	 * @return la cuenta origen de la transacción
	 */
	public Cuenta getOrigen() {
		return origen;
	}


	/**
	 * @param origen la cuenta origen de la transacción
	 */
	public void setOrigen(Cuenta origen) {
		this.origen = origen;
	}


	/**
	 * @return la cuenta destino de la transacción
	 */
	public Cuenta getDestino() {
		return destino;
	}


	/**
	 * @param destino la cuenta destino de la transacción
	 */
	public void setDestino(Cuenta destino) {
		this.destino = destino;
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
		Transaccion other = (Transaccion) obj;
		return Objects.equals(id, other.id);
	}


	@Override
	public String toString() {
		return "Transaccion = {\n\tid:" + id + ",\n\tfechaInstruccion:" + fechaInstruccion + ",\n\tcantidad:" + cantidad
				+ ",\n\tfechaEjecucion:" + fechaEjecucion + ",\n\ttipo:" + tipo + ",\n\tcomision:" + comision
				+ ",\n\tinternacional:" + internacional + "\n}";
	}
	
	
	
}