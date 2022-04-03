package es.uma.softcoders.eburyApp;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CUENTA_FINTECH")
@Inheritance(strategy = InheritanceType.JOINED)
public class CuentaFintech extends Cuenta{
	
	public CuentaFintech() {}
	
	
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
	 * @return clasificacion
	 */
	public String getClasificacion() {
		return clasificacion;
	}

	/**
	 * @param clasificacion indica el tipo de cuenta
	 */
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CuentaFintech other = (CuentaFintech) obj;
		return super.getIban() == other.getIban();
	}

	@Override
	public String toString() {
		return "CuentaFintech [IBAN=" + super.getIban() + ", estado=" + estado + ", fechaApertura=" + fechaApertura + ", fechaCierre="
				+ fechaCierre + ", clasificacion=" + clasificacion + "]";
	}
		
}
