package es.uma.softcoders.eburyApp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cliente {
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
    private Date Fecha_Alta;
    
    private Date Fecha_Baja;
    
    @Column (nullable = false)
    private String Direccion;
    
    @Column (nullable = false)
    private String Ciudad;
    
    @Column (nullable = false)
    private int CodigoPostal;
    
    @Column (nullable = false)
    private String Pais;
    

    @OneToMany (mappedBy = "Cliente_cuenta") // Se usa en la relacion pero lo dejo definido
    private List<Cuenta> cuentas;
    
    @OneToMany (mappedBy = "Persona_aut")
    private List<Persona_autorizada> PersonasAut;
    // ATRIBUTOS ---------------------------------------------------------------------------------------------------------------------
	public Cliente(){
		
	}
	
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
	
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Ciudad == null) ? 0 : Ciudad.hashCode());
		result = prime * result + CodigoPostal;
		result = prime * result + ((Direccion == null) ? 0 : Direccion.hashCode());
		result = prime * result + ((Fecha_Alta == null) ? 0 : Fecha_Alta.hashCode());
		result = prime * result + ((Fecha_Baja == null) ? 0 : Fecha_Baja.hashCode());
		result = prime * result + (int) (ID ^ (ID >>> 32));
		result = prime * result + ((Identificacion == null) ? 0 : Identificacion.hashCode());
		result = prime * result + ((Pais == null) ? 0 : Pais.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((tipo_cliente == null) ? 0 : tipo_cliente.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (Ciudad == null) {
			if (other.Ciudad != null)
				return false;
		} else if (!Ciudad.equals(other.Ciudad))
			return false;
		if (CodigoPostal != other.CodigoPostal)
			return false;
		if (Direccion == null) {
			if (other.Direccion != null)
				return false;
		} else if (!Direccion.equals(other.Direccion))
			return false;
		if (Fecha_Alta == null) {
			if (other.Fecha_Alta != null)
				return false;
		} else if (!Fecha_Alta.equals(other.Fecha_Alta))
			return false;
		if (Fecha_Baja == null) {
			if (other.Fecha_Baja != null)
				return false;
		} else if (!Fecha_Baja.equals(other.Fecha_Baja))
			return false;
		if (ID != other.ID)
			return false;
		if (Identificacion == null) {
			if (other.Identificacion != null)
				return false;
		} else if (!Identificacion.equals(other.Identificacion))
			return false;
		if (Pais == null) {
			if (other.Pais != null)
				return false;
		} else if (!Pais.equals(other.Pais))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (tipo_cliente == null) {
			if (other.tipo_cliente != null)
				return false;
		} else if (!tipo_cliente.equals(other.tipo_cliente))
			return false;
		return true;
	}
	
}
