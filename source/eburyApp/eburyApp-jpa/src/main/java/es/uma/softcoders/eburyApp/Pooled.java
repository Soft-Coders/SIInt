package es.uma.softcoders.eburyApp;

import java.io.Serializable;
import java.util.Map;
import java.util.StringJoiner;

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
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6420219173607591585L;
	
	// ---------- ATRIBUTOS ----------
	
	// ID es heredado de CuentaFintech, que lo hereda de Cuenta
	
    @ElementCollection
    @CollectionTable(name="DEPOSITADA_EN",
                     joinColumns=@JoinColumn(name="POOLED_FK"))
    @Column(name="SALDO")
    @MapKeyJoinColumn(name="CUENTA_REFERENCIA_FK", referencedColumnName="iban")
    private Map<CuentaReferencia, Long> depositadaEn;

	@Override
	public String toString() {
		StringJoiner depositadaEnStr = new StringJoiner(",\n\t","{\n", "\n}");
		
		for(Map.Entry<CuentaReferencia, Long> entry : depositadaEn.entrySet()) {
			depositadaEnStr.add("CuentaReferencia: " + entry.getKey().getIban() + ", Saldo: " + entry.getValue().longValue());
		}
		
		return "Pooled = {\n\t" + super.toString() + ",\n\tdepositadaEn: " + depositadaEnStr + "\n}";
	}
    
    
    
    
}
