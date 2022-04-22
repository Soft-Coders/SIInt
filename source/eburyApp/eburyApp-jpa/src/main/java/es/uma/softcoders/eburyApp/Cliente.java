package es.uma.softcoders.eburyApp;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "CLIENTE")
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente implements Serializable{
	
	private static final long serialVersionUID = 2924062538262296321L;
	
	public Cliente(){
		super();
	}
	public Cliente(String ident, String tipoCli, String est, Date fechAlt, String dire, String ciud, int codPos, String Pais){

		this.identificacion = ident;
		this.tipoCliente = tipoCli;
		this.estado = est;
		this.fechaAlta = fechAlt;
		this.direccion = dire;
		this.ciudad = ciud;
		this.codigoPostal = codPos;
		this.pais = Pais;

	}

	public Cliente(String ident, String tipoCli, String est, Date fechAlt, Date fechBaj, String dire, String ciud, int codPos, String Pais){

		this.identificacion = ident;
		this.tipoCliente = tipoCli;
		this.estado = est;
		this.fechaAlta = fechAlt;
		this.fechaBaja = fechBaj;
		this.direccion = dire;
		this.ciudad = ciud;
		this.codigoPostal = codPos;
		this.pais = Pais;

	}
	
	// ---------- ATRIBUTOS ----------

	@Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    
    @Column (nullable = false)
    private String identificacion;
    
    @Column (nullable = false)
    private String tipoCliente;
    
    @Column (nullable = false)
    private String estado;

    @Column (nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    
    @Temporal(TemporalType.DATE)
    private Date fechaBaja;
    
    @Column (nullable = false)
    private String direccion;
    
    @Column (nullable = false)
    private String ciudad;
    
    @Column (nullable = false)
    private int codigoPostal;
    
    @Column (nullable = false)
    private String pais;
    
    // --------- RELACIONES ---------
    	
	@OneToMany (mappedBy = "cliente") 
    private List<CuentaFintech> cuentas;

    // ------ GETTERS & SETTERS ------
	
	public Long getID() {
		return id;
	}
	public void setID(Long iD) {
		id = iD;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String Identificacion) {
		identificacion = Identificacion;
	}
	public String getTipo_cliente() {
		return tipoCliente;
	}
	public void setTipo_cliente(String tipo_cliente) {
		this.tipoCliente = tipo_cliente;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFecha_Alta() {
		return fechaAlta;
	}
	public void setFecha_Alta(Date fecha_Alta) {
		fechaAlta = fecha_Alta;
	}
	public Date getFecha_Baja() {
		return fechaBaja;
	}
	public void setFecha_Baja(Date fecha_Baja) {
		fechaBaja = fecha_Baja;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String Direccion) {
		direccion = Direccion;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String Ciudad) {
		ciudad = Ciudad;
	}
	public int getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(int CodigoPostal) {
		codigoPostal = CodigoPostal;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String Pais) {
		pais = Pais;
	}
	
	
	/**
	 * @return la lista de cuentas del cliente
	 */
	public List<CuentaFintech> getCuentas() {
		return cuentas;
	}
	/**
	 * @param cuentas la lista de cuentas fintech del cliente
	 */
	public void setCuentas(List<CuentaFintech> cuentas) {
		this.cuentas = cuentas;
	}
	
	
    @Override
	public int hashCode() {
		int res = this.id.hashCode();
		res += this.identificacion.toLowerCase().hashCode();
		return res;
	}
	@Override
	public boolean equals(Object obj) {
		boolean res = true;
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			res = false;
		Cliente other = (Cliente) obj;
		if (this.id!= other.getID() || !(this.identificacion.equalsIgnoreCase(other.getIdentificacion())))
			res = false;

		return res;
	}

	@Override
	public String toString() {
		return "Cliente = {\n\tid: " + id + ", \n\tidentificacion: " + identificacion + ", \n\rtipoCliente: " + 
				tipoCliente + ", \n\testado: " + estado + ", \n\tfechaAlta: " + fechaAlta + ", \n\tfechaBaja: " + 
				fechaBaja + ", \n\tdireccion: " + direccion + ", \n\tciudad: " + ciudad + ", \n\tcodigoPostal: " + 
				codigoPostal + ", \n\tpais: " + pais + ", \n\tcuentas: " + cuentas + "\n}";
	}
	
}
