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
	
	@Inject
	private GestionCuenta cuentaEJB;
	
	private String usuario;
	private List<CuentaFintech> listaCuentasPropias;
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
    }
    
    public void getCuentasPropias() {
    	cuentaEJB.getCuentasFintechPropias(usuario);
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
    public String vistaPrincipalCliente() {
    	return "vistaPrincipalCliente.xhtml";
    }
} 
