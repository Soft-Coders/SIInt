package es.uma.softcoders.eburyApp;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Divisa {

	@Id
	@Column( nullable = false, length = 3 )
	private String abreviatura;
	
	@Column( nullable = false )
	private String nombre;
	
	private char simbolo;
	
	@Column( name = "cambio_euro", nullable = false )
	private double cambioEuro;
	
	public Divisa() {
		
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public char getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(char simbolo) {
		this.simbolo = simbolo;
	}

	public double getCambioEuro() {
		return cambioEuro;
	}

	public void setCambioEuro(double cambioEuro) {
		this.cambioEuro = cambioEuro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(abreviatura, cambioEuro, nombre, simbolo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Divisa other = (Divisa) obj;
		return Objects.equals(abreviatura, other.abreviatura)
				&& Double.doubleToLongBits(cambioEuro) == Double.doubleToLongBits(other.cambioEuro)
				&& Objects.equals(nombre, other.nombre) && simbolo == other.simbolo;
	}

	@Override
	public String toString() {
		return "Divisa [abreviatura=" + abreviatura + ", nombre=" + nombre + ", simbolo=" + simbolo + ", cambioEuro="
				+ cambioEuro + "]";
	}
	
	
	
	
}
