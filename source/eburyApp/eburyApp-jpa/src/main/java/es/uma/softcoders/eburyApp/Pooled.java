package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name= "POOLED")
public class Pooled extends CuentaFintech implements Serializable{
	
	private static final long serialVersionUID = 6420219173607591585L;
	
	public Pooled() {
		super();
	}
	
	public Pooled(Map<CuentaReferencia, Double> depositadaEn) {
		this.depositadaEn = depositadaEn;
	}
	
	// ---------- ATRIBUTOS ----------
	
	// ID es heredado de CuentaFintech, que lo hereda de Cuenta
	
    @ElementCollection
    @CollectionTable(name="DEPOSITADA_EN",
                     joinColumns=@JoinColumn(name="POOLED_FK"))
    @Column(name="SALDO")
    @MapKeyJoinColumn(name="CUENTA_REFERENCIA_FK", referencedColumnName="iban")
    private Map<CuentaReferencia, Double> depositadaEn;

    // ------ GETTERS & SETTERS ------
    
	/**
	 * @return CuentasReferencia asociadas con sus respectivos saldos
	 */
	public Map<CuentaReferencia, Double> getDepositadaEn() {
		return depositadaEn;
	}

	/**
	 * @param depositadaEn las CuentasReferencia asociadas con sus respectivos saldos
	 */
	public void setDepositadaEn(Map<CuentaReferencia, Double> depositadaEn) {
		this.depositadaEn = depositadaEn;
	}

	@Override
	public String toString() {
		return "Pooled = {\n\t" + super.toString() + "\n}";
	}
    
    
    
    
}
