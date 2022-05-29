package es.uma.softcoders.eburyApp.backing;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.ejb.GestionAutorizado;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;


@Named(value = "autorizacion")
@RequestScoped
public class Autorizacion {
    
	@Inject
	private AutorizadoController autorizado;
	
	@EJB
    private GestionAutorizado gestionAutorizado;
	
	private PersonaAutorizada autorizadoAux;
    
    private String empresa;
    
    private String tipo;
	
	public String autorizar(String paut) {
    	char charAux = tipo.charAt(0);
    	
    	try {
    		System.out.println("antes de autorizar");
			gestionAutorizado.autorizar(paut, empresa, charAux);
			System.out.println("después de autorizar");
		} catch (EburyAppException e) {
			System.out.println("Ha encontrado un error" + e.getMessage() + " "+e.getClass() + " "+ e.getStackTrace());
            FacesMessage fm = new FacesMessage("Error al autorizar");
            FacesContext.getCurrentInstance().addMessage("bajaAutorizados:list", fm);
        }
			
		
    	return autorizado.refrescarBaja();
    }

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public PersonaAutorizada getAutorizadoAux() {
		return autorizadoAux;
	}

	public void setAutorizadoAux(PersonaAutorizada autorizadoAux) {
		this.autorizadoAux = autorizadoAux;
	}
}
