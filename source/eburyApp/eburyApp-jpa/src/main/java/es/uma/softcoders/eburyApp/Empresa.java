package es.uma.softcoders.eburyApp;
import java.io.Serializable;
import java.util.Map;

import javax.persistence.*;

@Entity
@Table(name="EMPRESA")
public class Empresa extends Cliente implements Serializable{
    
	private static final long serialVersionUID = 1735503934250648278L;
	
	// ---------- ATRIBUTOS ----------
	
    @Column(name = "FECHA_NACIMIENTO", nullable = false, length = 30)
    private String razonSocial;
    
    // ------ GETTERS & SETTERS ------
     
    public Empresa(){
        super();
    }
    
    public Empresa(String raz){
        super();
        this.razonSocial = raz;
    }

    public void setRazonSocial(String razSoc){
        this.razonSocial = razSoc;
    }

    public String getRazonSocial(){
        return this.razonSocial;
    }

    /**
	 * @param aut el mapa de autorizaciones de una empresa
	 */
    public void setAutorizacion(Map<PersonaAutorizada, Character> aut){
        this.autorizacion = aut;
    }
    
    /**
	 * @return el mapa de autorizaciones de una empresa
	 */
    public Map<PersonaAutorizada, Character> getAutorizacion(){
        return this.autorizacion;
    }
    
    // --------- RELACIONES ---------
    
    @ElementCollection
    @CollectionTable(name="AUTORIZACION",
                     joinColumns=@JoinColumn(name="EMPRESA_FK"))
    @MapKeyJoinColumn(name="PERSONA_AUTORIZADA_FK", referencedColumnName="id")
    @Column(name="TIPO")
    private Map<PersonaAutorizada, Character> autorizacion;
    
    // HASHCODE & EQUALS SE HEREDAN DE CLIENTE 

    
    public String toString() {
		return "Empresa = {\n\t" + super.toString() + "\n\trazonSocial: " + razonSocial + "\n}";
	}

}
