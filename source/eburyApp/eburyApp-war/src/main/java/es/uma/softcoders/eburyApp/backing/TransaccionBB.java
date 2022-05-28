package es.uma.softcoders.eburyApp.backing;

import javax.ejb.EJB;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import es.uma.softcoders.eburyApp.ejb.GestionTransaccion;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;



@Named
@RequestScoped
public class TransaccionBB {
	
	@EJB
	private GestionTransaccion transaccionEJB;
	
    private String iban;
    private String divisaOrigen;
    private String divisaDestino;
    private Double cantidad;
    
    public String getIban() {
    	return iban;
    }
    public void setIban(String i) {
    	iban = i;
    }
    
    public String getDivisaOrigen() {
    	return divisaOrigen;
    }
    public void setDivisaOrigen(String d) {
    	divisaOrigen = d;
    }
    
    public String getDivisaDestino() {
    	return divisaDestino;
    }
    public void setDivisaDestino(String d) {
    	divisaDestino = d;
    }
    
    public Double getCantidad() {
    	return cantidad;
    }
    public void setCantidad(Double c) {
    	cantidad = c;
    }
    
    public String preguntarConfirmacionCambio() {
    	return "vistaConfirmarCambio.xhtml";
    }
    public void crearCambioDivisas() throws EburyAppException {
    	transaccionEJB.cambioDivisa(iban, divisaOrigen, divisaDestino, cantidad);
    }
    public String volverVistaSaldo() {
    	return "vistaCuentaSaldo.xhtml";
    }
} 
