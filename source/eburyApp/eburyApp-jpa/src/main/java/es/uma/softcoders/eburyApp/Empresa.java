package es.uma.softcoders.eburyApp;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import org.eclipse.persistence.internal.expressions.FromAliasExpression;
import org.eclipse.persistence.internal.jpa.metadata.xml.EmptyElementConverter;

@Entity
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
    
    @Override
    public int hashCode() {
		int res = (int) this.getID();
		res += this.getIdentificacion().toLowerCase().hashCode() + 
        this.razonSocial.toLowerCase().hashCode();
		return res;
	}

    @Override
    public boolean equals(Object obj){
        boolean res = false;
		if (this == obj)
			res = true;
		if (obj == null)
			res = false;
		if (getClass() != obj.getClass())
			res = false;

		Empresa other = (Empresa) obj;

		if (this.getID() != other.getID() || 
        !(this.getIdentificacion().equalsIgnoreCase(other.getIdentificacion())))
			res = false;

        if (this.razonSocial != other.razonSocial)
            res = false;
		return res;
    }

}
