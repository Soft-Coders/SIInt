package es.uma.softcoders.eburyApp;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Cliente implements Serializable{
    // ATRIBUTOS ---------------------------------------------------------------------------------------------------------------------
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long ID;
    
    @Column (nullable = false)
    private String Identificacion;
    
    @Column (nullable = false)
    private String tipo_cliente;
    
    @Column (nullable = false)
    private String estado;
    @Column (nullable = false)

    @Temporal(TemporalType.DATE)
    private Date Fecha_Alta;
    
    @Temporal(TemporalType.DATE)
    private Date Fecha_Baja;
    
    @Column (nullable = false)
    private String Direccion;
    
    @Column (nullable = false)
    private String Ciudad;
    
    @Column (nullable = false)
    private int CodigoPostal;
    
    @Column (nullable = false)
    private String Pais;
    
	// RELACIONES
	/*
	@OneToMany (mappedBy = "Cliente_cuenta") // Se usa en la relacion pero lo dejo definido
    private List<Cuenta> cuentas;
    
    @OneToMany (mappedBy = "Persona_aut")
    private List<Persona_autorizada> PersonasAut;
	*/

    // ATRIBUTOS ---------------------------------------------------------------------------------------------------------------------
	
	
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public String getIdentificacion() {
		return Identificacion;
	}
	public void setIdentificacion(String identificacion) {
		Identificacion = identificacion;
	}
	public String getTipo_cliente() {
		return tipo_cliente;
	}
	public void setTipo_cliente(String tipo_cliente) {
		this.tipo_cliente = tipo_cliente;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFecha_Alta() {
		return Fecha_Alta;
	}
	public void setFecha_Alta(Date fecha_Alta) {
		Fecha_Alta = fecha_Alta;
	}
	public Date getFecha_Baja() {
		return Fecha_Baja;
	}
	public void setFecha_Baja(Date fecha_Baja) {
		Fecha_Baja = fecha_Baja;
	}
	public String getDireccion() {
		return Direccion;
	}
	public void setDireccion(String direccion) {
		Direccion = direccion;
	}
	public String getCiudad() {
		return Ciudad;
	}
	public void setCiudad(String ciudad) {
		Ciudad = ciudad;
	}
	public int getCodigoPostal() {
		return CodigoPostal;
	}
	public void setCodigoPostal(int codigoPostal) {
		CodigoPostal = codigoPostal;
	}
	public String getPais() {
		return Pais;
	}
	public void setPais(String pais) {
		Pais = pais;
	}
	/*
	public List<Cuenta> getCuentas() {
		return cuentas;
	}
	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
	
	public List<Persona_autorizada> getPersonasAut() {
		return PersonasAut;
	}
	public void setPersonasAut(List<Persona_autorizada> personasAut) {
		PersonasAut = personasAut;
	}
	*/
    @Override
	public int hashCode() {
		int res = (int) this.ID;
		res += this.Identificacion.toLowerCase().hashCode();
		return res;
	}
	@Override
	public boolean equals(Object obj) {
		boolean res = false;
		if (this == obj)
			res = true;
		if (obj == null)
			res = false;
		if (getClass() != obj.getClass())
			res = false;
		Cliente other = (Cliente) obj;
		if (this.ID != other.getID() || !(this.Identificacion.equalsIgnoreCase(other.getIdentificacion())))
			res = false;

		return res;
	}
}
