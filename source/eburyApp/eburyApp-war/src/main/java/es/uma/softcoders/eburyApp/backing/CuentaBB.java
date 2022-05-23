package es.uma.softcoders.eburyApp.backing;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import es.uma.softcoders.eburyApp.ejb.GestionCuenta;
import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;



@Named
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
	private List<CuentaFintech> listaEmpresasAutorizadas;
	private Empresa emp;   //emp guarda la empresa que ha sido seleccionada
	private List<Empresa> listaCuentasAutorizadas;
    private CuentaFintech cf;
    private String iban;
    
    public CuentaFintech getCuentaFintech () {
    	return cf;
    }
    public void setCuentaFintech(CuentaFintech cuenta) {
    	this.cf = cuenta;
    }
    
    public String getIban() {
    	return iban;
    }
    public void se tIban(String i) {
    	iban = i;
    }
   
    public Usuario getUsuario() {
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
    	this.empresa = emp;
    	getCuentasAutorizadas();
    	return "vistaEmpresaSeleccionada.xhtml"
    }
    public String seleccionarCuenta(String cuenta) {
    	this.cf = cuenta;
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
    	listaCuentasAutorizadas = cuentaEJB.getCuentasAutorizadas(usuario, emp);
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
    	return "vistaConfirmarEliminar.xhtml";
    }
    
    //IMPLEMENTAR CAMBIO DE DIVISA
    public String irCambioDivisa() {
    	trBB.setIban(iban);
    	return "vistaCambioDivisa.xhtml";
    }
} 
