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
    private CuentaFintech cuentaFin;
    private String iban;
    
    public CuentaFintech getCuentaFintech () {
    	return cuentaFin;
    }
    public void setCuentaFintech(CuentaFintech cf) {
    	this.cuentaFin = cf;
    }
    
    public String getIban() {
    	return iban;
    }
    public void setIban(String i) {
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
		cuentaEJB.crearCuentaFintech(cuentaFin);
    }

    public void cerrarCuentaF() throws EburyAppException {
		cuentaEJB.cerrarCuentaFintech(iban);
    }
} 
