package es.uma.softcoders.eburyApp;
import java.util.Map;

import javax.persistence.*;

@Entity
@Table(name="EMPRESA")
public class Empresa extends Cliente {
    
    // ---------- ATRIBUTOS ----------
    @Column(name = "FECHA_NACIMIENTO", nullable = false, length = 30)
    private String razonSocial;
    
    // ------ GETTERS & SETTERS ------
     
    public Empresa(){
        super();
    }

    public void setRazonSocial(String razSoc){
        this.razonSocial = razSoc;
    }

    public String getRazonSocial(){
        return this.razonSocial;
    }

    @ElementCollection
    @CollectionTable(name="AUTORIZACION",
                     joinColumns=@JoinColumn(name="PERSONA_AUTORIZADA_FK"))
    @Column(name="TIPO")
    @MapKeyJoinColumn(name="PersonaAutorizada", referencedColumnName="ID")
    private Map<PersonaAutorizada, Character> autorizacion;
    
    // HASHCODE & EQUALS SE HEREDAN DE CLIENTE 

    
    public String toString() {
		return "Empresa = {\n\t" + super.toString() + "\n\trazonSocial: " + razonSocial + "\n}";
	}

}
