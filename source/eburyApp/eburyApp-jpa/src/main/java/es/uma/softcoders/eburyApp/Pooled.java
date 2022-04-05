package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

@Entity
@Table(name= "POOLED")
/*@PrimaryKeyJoinColumns({
	@PrimaryKeyJoinColumn(name = "CODIGO_PAIS_POOL", referencedColumnName = "CODIGO_PAIS_FINTECH"),
	@PrimaryKeyJoinColumn(name = "NUMERO_CUENTA_POOL", referencedColumnName = "NUMERO_CUENTA_FINTECH")
	})*/
//@DiscriminatorValue(value = "P")
public class Pooled extends CuentaFintech implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6420219173607591585L;
	
	// ---------- ATRIBUTOS ----------
	
	/*
	// ID es heredado de CuentaFintech, que lo hereda de Cuenta
	@ManyToMany(mappedBy = "cuentasPooled")
	@MapKeyColumn(name = "SALDO", nullable = false)
	private Map<CuentaReferencia, Double> cuentasRef;
	
	*/
}
