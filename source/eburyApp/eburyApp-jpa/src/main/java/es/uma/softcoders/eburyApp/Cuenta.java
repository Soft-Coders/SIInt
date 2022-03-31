package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cuenta implements Serializable{
	
		@Id
		private Long IBAN;    //Solo la parte numérica
		@Column(length=2, nullable = false)
		private String codigo_pais;  //El inicio del IBAN
		private String swift;
		
		// Constructor
		public Cuenta() {

		}
		
		// Métodos de apoyo
		public Long getIBAN() {
			return IBAN;
		}

		public void setIBAN(Long iBAN) {
			IBAN = iBAN;
		}

		public String getCodigo_pais() {
			return codigo_pais;
		}

		public void setCodigo_pais(String codigo_pais) {
			this.codigo_pais = codigo_pais;
		}

		public String getSwift() {
			return swift;
		}

		public void setSwift(String swift) {
			this.swift = swift;
		}

		@Override
		public int hashCode() {
			return Objects.hash(IBAN, codigo_pais, swift);
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
			return Objects.equals(IBAN, other.IBAN) && Objects.equals(codigo_pais, other.codigo_pais)
					&& Objects.equals(swift, other.swift);
		}

		@Override
		public String toString() {
			return "Cuenta [IBAN=" + IBAN + ", codigo_pais=" + codigo_pais + ", swift=" + swift + "]";
		}
		
}
