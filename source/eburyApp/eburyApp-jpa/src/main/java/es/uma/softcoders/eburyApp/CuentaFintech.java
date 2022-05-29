package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
//@Table(name="CUENTA_FINTECH")

public class CuentaFintech extends Cuenta implements Serializable{
	
	private static final long serialVersionUID = -6441702807546365605L;

	public CuentaFintech() {
		super();
	}
	
	public CuentaFintech(String estado) {
		this.estado = estado;
		this.fechaApertura = new Date();
	}
	
	public CuentaFintech(String estado, Date fechaApertura) {
		this.estado = estado;
		this.fechaApertura = fechaApertura;
	}
	
	public CuentaFintech(String estado, Date fechaApertura, Date fechaCierre, String clasificacion) {
		this.estado = estado;
		this.fechaApertura = fechaApertura;
		this.fechaCierre = fechaCierre;
		this.clasificacion = clasificacion;
	}
	
	
	// ----------- ATRIBUTOS -----------
	
	@Column(nullable=false)
	private String estado;
	
	@Column(name="FECHA_APERTURA", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fechaApertura;
	
	@Column(name="FECHA_CIERRE")
	@Temporal(TemporalType.DATE)
	private Date fechaCierre;
	
	private String clasificacion;
	
	
	// ---------- RELACIONES -----------
	
	/**
	 * @return la clasificación de la cuenta
	 */
	public String getClasificacion() {
		return clasificacion;
	}

	/**
	 * @param clasificacion la clasificación de la cuenta
	 */
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}


	@ManyToOne
	@JoinColumn(name="CLIENTE_FK", nullable=false)
	private Cliente cliente;
	
	
	// ------ GETTERS & SETTERS ------ 
	
	/**
	 * @return el estado de la cuenta
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado indica la situación en que se encuentra la cuenta
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return fechaApertura
	 */
	public Date getFechaApertura() {
		return fechaApertura;
	}

	/**
	 * @param fechaApertura señala la fecha en que se abrió la cuenta
	 */
	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	/**
	 * @return fechaCierre
	 */
	public Date getFechaCierre() {
		return fechaCierre;
	}

	/**
	 * @param fechaCierre señala la fecha en que se cerró la cuenta
	 */
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	
	/**
 	 * @return el cliente dueño de la cuentaFintech
 	 */
 	public Cliente getCliente() {
 		return cliente;
 	}

 	/**
 	 * @param cliente indica el id del dueño de la cuentaFintech
 	 */
 	public void setCliente(Cliente cliente) {
 		this.cliente = cliente;
 	}
	
	// equals() y hashCode() se heredan del padre

	
	@Override
	public String toString() {
		return "CuentaFintech = {\n\t"+ super.toString() +"\n\testado: " + estado + ",\n\tfechaApertura: " + fechaApertura + 
				",\n\tfechaCierre: " + fechaCierre + "\n}";
	}
	
}
