package es.uma.softcoders.eburyApp;
import java.util.Date;
import java.util.Map;

import javax.persistence.*;


@Entity
@Table(name="INDIVIDUAL")
public class Individual extends Cliente{
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

    @ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "AUTORIZACION", joinColumns = @JoinColumn(name = "INDIVIDUAL_FK"))
	@MapKeyJoinColumn(name = "PERSONA_AUT_FK")   
	private Map<PersonaAutorizada,String> personaAut; 
	

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

    //  HASHCODE & EQUALS SE HEREDAN DE CLIENTE

    public String toString() {
		return "Individual[" + this.getID() +" , " + this.getIdentificacion() + " , " + this.nombre + " , " + this.apellido + "]";
	}

    
}
