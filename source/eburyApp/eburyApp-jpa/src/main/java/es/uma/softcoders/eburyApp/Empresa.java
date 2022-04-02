package es.uma.softcoders.eburyApp;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import org.eclipse.persistence.internal.expressions.FromAliasExpression;
import org.eclipse.persistence.internal.jpa.metadata.xml.EmptyElementConverter;

@Entity
@Table(name="EMPRESA")
public class Empresa extends Cliente {
     // ATRIBUTOS ---------------------------------------------------------------------------------------------------------------------
    @Column(name = "FECHA_NACIMIENTO", nullable = false, length = 30)
    private String razonSocial;
    
     // ATRIBUTOS ---------------------------------------------------------------------------------------------------------------------
     
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
