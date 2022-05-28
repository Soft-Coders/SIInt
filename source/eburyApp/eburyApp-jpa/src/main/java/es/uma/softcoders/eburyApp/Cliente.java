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
	public Cliente(String ident, String tipoCli, String est, Date fechAlt, String dire, String ciud, String codPos, String Pais){

		this.identificacion = ident;
		this.tipoCliente = tipoCli;
		this.estado = est;
		this.fechaAlta = fechAlt;
		this.direccion = dire;
		this.ciudad = ciud;
		this.codigoPostal = codPos;
		this.pais = Pais;

	}

	public Cliente(String ident, String tipoCli, String est, Date fechAlt, Date fechBaj, String dire, String ciud, String codPos, String Pais){

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
    private String codigoPostal;
    
    @Column (nullable = false)
    private String pais;
    
    // --------- RELACIONES ---------
    	
	@OneToMany (mappedBy = "cliente") 
    private List<CuentaFintech> cuentas;

    // ------ GETTERS & SETTERS ------
	
	/**
	 * @return la lista de cuentas del cliente
	 */
	public List<CuentaFintech> getCuentas() {
		return cuentas;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the identificacion
	 */
	public String getIdentificacion() {
		return identificacion;
	}
	/**
	 * @param identificacion the identificacion to set
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	/**
	 * @return the tipoCliente
	 */
	public String getTipoCliente() {
		return tipoCliente;
	}
	/**
	 * @param tipoCliente the tipoCliente to set
	 */
	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the fechaAlta
	 */
	public Date getFechaAlta() {
		return fechaAlta;
	}
	/**
	 * @param fechaAlta the fechaAlta to set
	 */
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	/**
	 * @return the fechaBaja
	 */
	public Date getFechaBaja() {
		return fechaBaja;
	}
	/**
	 * @param fechaBaja the fechaBaja to set
	 */
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	/**
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}
	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}
	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	/**
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}
	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
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
		if (this.id!= other.getId() || !(this.identificacion.equalsIgnoreCase(other.getIdentificacion())))
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
