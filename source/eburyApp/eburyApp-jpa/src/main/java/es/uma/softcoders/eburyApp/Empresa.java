package es.uma.softcoders.eburyApp;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="EMPRESA")
public class Empresa extends Cliente {
    
    // ---------- ATRIBUTOS ----------
    @Column(name = "FECHA_NACIMIENTO", nullable = false, length = 30)
    private String razonSocial;
    
    @OneToMany (mappedBy = "Persona_aut")
    private List<PersonaAutorizada> PersonasAut;
    
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
    
    // HASHCODE & EQUALS SE HEREDAN DE CLIENTE 

    
    public String toString() {
		return "Empresa[" + this.getID() +" , " + this.getIdentificacion() + " , " + this.razonSocial+ "]";
	}

}
