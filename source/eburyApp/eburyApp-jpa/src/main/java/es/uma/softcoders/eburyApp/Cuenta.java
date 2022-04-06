package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Cuenta implements Serializable{


	public Cuenta() {}
	
	// ----------- ATRIBUTOS -----------
	
	@Id
	@Column(nullable=false)
	private String iban;
	
	private String swift;

	
	// ------ GETTERS & SETTERS ------
	
	/**
	 * @return el iban
	 */
	public String getIban() {
		return iban;
	}

	/**
	 * @param iban es la clave primaria de la cuenta
	 */
	public void setIban(String iban) {
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
		return Objects.hash(iban);
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
		return Objects.equals(iban, other.iban);
	}

	@Override
	public String toString() {
		return "Cuenta = {\n\tiban: " + iban + ",\n\tswift: " + swift + "\n}";
	}
	
}
