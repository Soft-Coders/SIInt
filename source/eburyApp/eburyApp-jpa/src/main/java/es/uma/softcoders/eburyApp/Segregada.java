package es.uma.softcoders.eburyApp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name= "SEGREGADA")
public class Segregada extends CuentaFintech implements Serializable{

	private static final long serialVersionUID = -817987222673845151L;	
	
	public Segregada() {
		
	}
	
	// ---------- ATRIBUTOS ----------
	
	// ID es heredado de CuentaFintech, que lo hereda de Cuenta
	
	@Column(length= 20)
	private String comision;
	
	@OneToOne
	@JoinColumn(name= "CUENTA_REF_ID", nullable = false)
	private CuentaReferencia cuentaRef;
	

	// ------ GETTERS & SETTERS ------
	
	/**
	 * @return la comisión de la cuenta segregada
	 */
	public String getComision() {
		return comision;
	}

	/**
	 * @param comision la comisión de la cuenta segregada
	 */
	public void setComision(String comision) {
		this.comision = comision;
	}

	// equals() y hashCode() son heredados de CuenteFintech, que los hereda de Cuenta 	
	
	@Override
	public String toString() {
		return "Segregada = {\n\t" + super.toString() + ",\n\tcomision: " + comision  +"\n}";	//TODO ampliar con id de padre
	}
	
}
