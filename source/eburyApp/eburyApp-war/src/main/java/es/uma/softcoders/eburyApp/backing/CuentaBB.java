package es.uma.softcoders.eburyApp.backing;

import java.util.List;

import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Usuario;
import javax.ejb.EJB;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import es.uma.softcoders.eburyApp.ejb.GestionCuenta;
import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;



@Named(value="cuentaBB")
@RequestScoped
public class CuentaBB {
	
	@EJB
	private GestionCuenta cuentaEJB;
	
	@Inject
	private TransaccionBB trBB;
	
	private String usuario;
	private boolean individual;
	private boolean autorizado;
	private List<CuentaFintech> listaCuentasPropias;
	private List<Empresa> listaEmpresasAutorizadas;
	private String emp;   //emp guarda la empresa que ha sido seleccionada
	private List<CuentaFintech> listaCuentasAutorizadas;
	private CuentaFintech cf = new CuentaFintech();
    private String iban;
    
    
    public List<CuentaFintech> getListaCuentasPropias() {
		return listaCuentasPropias;
	}
	public void setListaCuentasPropias(List<CuentaFintech> listaCuentasPropias) {
		this.listaCuentasPropias = listaCuentasPropias;
	}
	public List<Empresa> getListaEmpresasAutorizadas() {
		return listaEmpresasAutorizadas;
	}
	public void setListaEmpresasAutorizadas(List<Empresa> listaEmpresasAutorizadas) {
		this.listaEmpresasAutorizadas = listaEmpresasAutorizadas;
	}
	public List<CuentaFintech> getListaCuentasAutorizadas() {
		return listaCuentasAutorizadas;
	}
	public void setListaCuentasAutorizadas(List<CuentaFintech> listaCuentasAutorizadas) {
		this.listaCuentasAutorizadas = listaCuentasAutorizadas;
	}

    
    public CuentaFintech getCf () {
    	return cf;
    }
    public void setCuentaFintech(CuentaFintech cuenta) {
    	this.cf = cuenta;
    }
    
    public String getIban() {
    	return iban;
    }
    public void setIban(String i) {
    	iban = i;
    }
   
    public String getUsuario() {
    	return usuario;
    }
    public void setUsuario(String u) {
    	usuario = u;
    	individual = cuentaEJB.esIndividual(u);
    	autorizado = cuentaEJB.esAutorizado(u);
    	if (individual == true) {
    		getCuentasPropias();
    	} else if (autorizado == true) {
    		getEmpresasAutorizadas();
    	}
    }
    public String seleccionarEmpresa(String empresa) {
    	this.emp = empresa;
    	getCuentasAutorizadas();
    	return "vistaEmpresaSeleccionada.xhtml";
    }
    public String seleccionarCuenta(String cuenta) {
    	this.cf = cuentaEJB.getCuentaFintech(cuenta);  
    	iban = cuentaEJB.getIbanCuenta(cuenta);
    	return "vistaCuentaSaldo.xhtml";
    }
    
    private void getCuentasPropias() {
    	listaCuentasPropias = cuentaEJB.getCuentasFintechPropias(usuario);
    }
    private void getEmpresasAutorizadas() {
    	listaEmpresasAutorizadas = cuentaEJB.getEmpresasAutorizadas(usuario);
    }
    private void getCuentasAutorizadas() {
    	listaCuentasAutorizadas = cuentaEJB.getCuentasAutorizadas(emp);
    }
    
    public void crearCuentaF() throws EburyAppException {
		cuentaEJB.crearCuentaFintech(cf);
    }

    public void cerrarCuentaF() throws EburyAppException {
		cuentaEJB.cerrarCuentaFintech(iban);
    }
    
    public String vistaCrearCuenta() {
		return "vistaCrearCuentaFintech.xhtml";
	}
    public String vistaEliminarCuenta() {
		
		return "vistaEliminarCuentaFintech.xhtml";
	}
    public String vistaPrincipalCliente() {
    	return "vistaPrincipalCliente.xhtml";
    }
    public String vistaConfirmarEliminar(){
		try {
			cerrarCuentaF();
		} catch (EburyAppException e) {
			e.printStackTrace();
		}
    	return "vistaPrincipalCliente.xhtml";
    }
    
    public String irCambioDivisa() {
    	trBB.setIban(iban);
    	return "vistaCambioDivisa.xhtml";
    }
} 
