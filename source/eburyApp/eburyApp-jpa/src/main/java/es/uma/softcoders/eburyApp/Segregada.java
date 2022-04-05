package es.uma.softcoders.eburyApp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

@Entity
@Table(name= "SEGREGADA")
@PrimaryKeyJoinColumns({
	@PrimaryKeyJoinColumn(name = "CODIGO_PAIS_SEG", referencedColumnName = "CODIGO_PAIS_FINTECH"),
	@PrimaryKeyJoinColumn(name = "NUMERO_CUENTA_SEG", referencedColumnName = "NUMERO_CUENTA_FINTECH")
	})
//@DiscriminatorValue(value = "S")
public class Segregada extends CuentaFintech implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -817987222673845151L;	
	
	// ---------- ATRIBUTOS ----------
	
	// ID es heredado de CuentaFintech, que lo hereda de Cuenta
	
	@Column(length= 20)
	private String comision;
	/*
	@OneToOne
	@JoinColumn(name= "CUENTA_REF_ID", nullable = false)
	private CuentaReferencia cuentaRef;
	*/
	public Segregada() {
		super();
	}
	
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
		return "Segregada [comision=" + comision + super.getEstado() +"]";	//TODO ampliar con id de padre
	}
	
}
