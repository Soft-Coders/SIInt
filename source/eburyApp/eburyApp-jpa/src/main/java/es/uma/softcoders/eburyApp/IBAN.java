package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IBAN implements Serializable{
	
	// ----------- ATRIBUTOS -----------
	@Column(name="NUMERO_CUENTA", nullable=false)
	private Long numeroCuenta;    //Solo la parte numérica
	
	@Column(name="CODIGO_PAIS", length=2, nullable=false)
	private String codigoPais;  //El inicio del IBAN
	
	public IBAN() {}

	// ------ GETTERS & SETTERS ------ 
	public Long getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(Long numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getCodigoPais() {
		return codigoPais;
	}

	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigoPais, numeroCuenta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IBAN other = (IBAN) obj;
		return Objects.equals(codigoPais, other.codigoPais) && Objects.equals(numeroCuenta, other.numeroCuenta);
	}

	@Override
	public String toString() {
		return "IBAN [numeroCuenta=" + numeroCuenta + ", codigoPais=" + codigoPais + "]";
	}
	
}
