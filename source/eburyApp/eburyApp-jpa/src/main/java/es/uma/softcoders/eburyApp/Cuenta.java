package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;

@Entity
// @Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Cuenta implements Serializable{
	
	public Cuenta() {}
	
	
	// ----------- ATRIBUTOS -----------
	
	@EmbeddedId
	private IBAN iban;
	
	private String swift;

	
	// ------ GETTERS & SETTERS ------
	
	/**
	 * @return el iban
	 */
	public IBAN getIban() {
		return iban;
	}

	/**
	 * @param iban es la clave primaria de la cuenta
	 */
	public void setIban(IBAN iban) {
		this.iban = iban;
	}

	/**
	 * @return el swift
	 */
	public String getSwift() {
		return swift;
	}

	/**
	 * @param swift es un código que se utiliza para identificar al banco beneficiario de una transacción internacional
	 */
	public void setSwift(String swift) {
		this.swift = swift;
	}

	@Override
	public int hashCode() {
		return Objects.hash(iban, swift);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuenta other = (Cuenta) obj;
		return Objects.equals(iban, other.iban) && Objects.equals(swift, other.swift);
	}

	@Override
	public String toString() {
		return "Cuenta [iban=" + iban + ", swift=" + swift + "]";
	}
	
}
