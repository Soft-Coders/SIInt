package es.uma.softcoders.eburyApp;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder.In;
import org.eclipse.persistence.internal.expressions.FromAliasExpression;

@Entity
@Table(name="INDIVIDUAL")
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

    //  HASHCODE & EQUALS SE HEREDAN DE CLIENTE

    public String toString() {
		return "Individual[" + this.getID() +" , " + this.getIdentificacion() + " , " + this.nombre + " , " + this.apellido + "]";
	}

    
}
