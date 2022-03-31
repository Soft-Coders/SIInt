package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Cuenta_fintech  implements Serializable{
	
	// Clave primaria -> hablar herencias con Nacho
	@Id   	// Id de prueba, para comprobar que se genere DDL
	private Long IBAN;
	
	@Column(nullable=false)
	private String estado;
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fecha_apertura;  //DATE -> String?????
	@Temporal(TemporalType.DATE)
	private Date fecha_cierre;
	private String clasificacion;
	
	// Constructor
	public Cuenta_fintech() {

	}
}
