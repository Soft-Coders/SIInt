package es.uma.softcoders.eburyApp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name= "SEGREGADA")
public class Segregada implements Serializable{
// La entidad implementa Serializable para poder ser enviada por la red en un array de bytes	
	
	// ---------- ATRIBUTOS ----------
	
	@Id
	@GeneratedValue
	private int id;	// TODO Sustituir por id heredado de Cuenta
	
	@Column(length= 20)
	private String comision;
	@OneToOne
	@JoinColumn(name= "CUENTA_REF_ID", columnDefinition= "NUMBER NOT NULL")
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

	
	
	// Para generar hashCode() e equals() se necesita a la clase padre Cuenta de la que hereda id
	
//		@Override
//		public int hashCode() {
//			//TODO
//		}
		
//		@Override
//		public boolean equals(Object obj) {
//			//TODO
//		}
	
	
	@Override
	public String toString() {
		return "Segregada [comision=" + comision + "]";	//TODO ampliar con id de padre
	}
	
}
