package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Divisa implements Serializable{

	@Id
	@Column( length = 3 )
	private String abreviatura;
	
	@Column( nullable = false )
	private String nombre;
	
	private Character simbolo;
	
	@Column( name = "CAMBIO_EURO", nullable = false )
	private Long cambioEuro;
	
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

	public Character getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(Character simbolo) {
		this.simbolo = simbolo;
	}

	public Long getCambioEuro() {
		return cambioEuro;
	}

	public void setCambioEuro(Long cambioEuro) {
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
		return Objects.equals(abreviatura, other.abreviatura) && Objects.equals(cambioEuro, other.cambioEuro)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(simbolo, other.simbolo);
	}

	@Override
	public String toString() {
		return "Divisa [abreviatura=" + abreviatura + ", nombre=" + nombre + ", simbolo=" + simbolo + ", cambioEuro="
				+ cambioEuro + "]";
	}
	
	
	
	
}
