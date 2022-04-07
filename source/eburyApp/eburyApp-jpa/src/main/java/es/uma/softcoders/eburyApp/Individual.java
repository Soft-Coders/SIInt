package es.uma.softcoders.eburyApp;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name="INDIVIDUAL")
public class Individual extends Cliente implements Serializable{
	
	private static final long serialVersionUID = 6599653177154446587L;
	
	// ---------- ATRIBUTOS ----------

	@Column(length = 30, nullable = false)
    private String nombre;

    @Column(length = 40, nullable = false)
    private String apellido;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_NACIMIENTO")
    private Date fechaNacimiento;

    @OneToOne
	@JoinColumn(name="INDIVIDUAL_USUARIO", nullable = false)
	private Usuario usuario;

    // ------ GETTERS & SETTERS ------

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
    /**
	 * @return el usuario asociado al cliente individual
	 */
    public Usuario getUsuario(){
        return this.usuario;
    }

    /**
	 * @param us el usuario a asociar a un cliente
	 */
    public void setUsuario(Usuario us){
        this.usuario = us;
    }

    //  HASHCODE & EQUALS SE HEREDAN DE CLIENTE

    public String toString() {
    	return "Individual = {\n\t" + super.toString() + "\n\tnombre: " + nombre + "\n\tapellido: " + apellido + "\n\tfechaNacimiento: " + fechaNacimiento + "\n}";
	}
    
}
