package es.uma.softcoders.eburyApp;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.eclipse.persistence.internal.expressions.FromAliasExpression;

@Entity
public class Individual extends Cliente{
     // ATRIBUTOS ---------------------------------------------------------------------------------------------------------------------
    @Column(length = 30, nullable = false)
    private String nombre;

    @Column(length = 40, nullable = false)
    private String apellido;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_NACIMIENTO")
    private Date fechaNacimiento;
     // ATRIBUTOS ---------------------------------------------------------------------------------------------------------------------

    public Individual(){
        super();
    }

    public String getNombre(){
        return this.nombre;
    }
    public String getApellido(){
        return this.apellido;
    }
    public Date getFechaNacimiento(){
        return this.fechaNacimiento;
    }

    public void setNombre(String nom){
        this.nombre = nom;
    }
    public void setApellido(String ape){
        this.apellido = ape;
    }
    public void setFechaNacimiento(Date fech){
        this.fechaNacimiento = fech;
    }

    @Override
    public int hashCode() {
		int res = (int) this.getID();
		res += this.getIdentificacion().toLowerCase().hashCode() + 
        this.nombre.toLowerCase().hashCode() + this.apellido.toLowerCase().hashCode();
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

		Individual other = (Individual) obj;

		if (this.getID() != other.getID() || 
        !(this.getIdentificacion().equalsIgnoreCase(other.getIdentificacion())))
			res = false;
        if (this.nombre != other.nombre || this.apellido != other.apellido)
            res = false;

		return res;
    }

    
}
