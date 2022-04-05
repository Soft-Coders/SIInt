package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IBAN implements Serializable{
	
	public IBAN() {}
	
	
	// ----------- ATRIBUTOS -----------
	
	@Column(name="NUMERO_CUENTA", nullable=false)
	private Long numeroCuenta;    //Solo la parte numérica
	
	@Column(name="CODIGO_PAIS", length=2, nullable=false)
	private String codigoPais;  //El inicio del IBAN

	
	// ------ GETTERS & SETTERS ------ 
	
	/**
	 * @return el numeroCuenta
	 */
	public Long getNumeroCuenta() {
		return numeroCuenta;
	}

	/**
	 * @param numeroCuenta es el código del IBAN sin la indicación del país al que procede
	 */
	public void setNumeroCuenta(Long numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	/**
	 * @return el codigoPais
	 */
	public String getCodigoPais() {
		return codigoPais;
	}

	/**
	 * @param codigoPais es el inicio del IBAN, indica el país al que procede la cuenta bancaria
	 */
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
		return "IBAN = {\n\tnumeroCuenta: " + numeroCuenta + ", \n\tcodigoPais: " + codigoPais + "\n}";
	}
	
}
