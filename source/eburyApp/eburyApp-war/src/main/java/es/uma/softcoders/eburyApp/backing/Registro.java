package es.uma.softcoders.eburyApp.backing;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Usuario;
import es.uma.softcoders.eburyApp.ejb.GestionAutorizado;
import es.uma.softcoders.eburyApp.ejb.GestionCliente;
import es.uma.softcoders.eburyApp.ejb.GestionLogin;
import es.uma.softcoders.eburyApp.ejb.GestionUsuario;
import es.uma.softcoders.eburyApp.exceptions.ClienteNoEncontradoException;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoCoincidenteException;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;
import es.uma.softcoders.eburyApp.exceptions.UsuarioYaExistenteException;

@Named(value = "registro")
@RequestScoped
public class Registro implements Serializable{

		@Inject
		private InfoSesion infoSesion;
	
		@EJB
		private GestionUsuario gestionUsuario;
		
		@EJB
		private GestionCliente gestionCliente;
		
		@EJB
		private GestionAutorizado gestionAutorizado;
	
	    private Usuario usuario;
	    private PersonaAutorizada autorizado;
	    private Individual individual;
	    private Empresa empresa;
	    private Character cuenta;
	
	    public Registro() {
    	    usuario = new Usuario();
    	    autorizado = new PersonaAutorizada();
    	    individual = new Individual();
    	    
    	    autorizado.setFechaInicio(new Date());
    	    individual.setFechaAlta(new Date());
        }
		
	    public Individual getIndividual() {
	    	return individual;
	    }
	    
	    public PersonaAutorizada getAutorizado() {
	    	return autorizado;
	    }
	    
		public Usuario getUsuario() {
			return usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}

		public Empresa getEmpresa() {
			return empresa;
		}

		public void setEmpresa(Empresa empresa) {
			this.empresa = empresa;
		}

		public Character getCuenta() {
			return cuenta;
		}

		public void setCuenta(Character cuenta) {
			this.cuenta = cuenta;
		}

		public void setAutorizado(PersonaAutorizada autorizado) {
			this.autorizado = autorizado;
		}

		public void setIndividual(Individual individual) {
			this.individual = individual;
		}
		
		public String registrarUsuario() {
			try {
				gestionUsuario.agregarUsuario(usuario.getUsuario(), usuario.getClave());
	            return "index.xhtml";
	        } catch (UsuarioYaExistenteException e) {
	            FacesMessage fm = new FacesMessage("Error: " + e);
	            FacesContext.getCurrentInstance().addMessage(null, fm);
	        } catch (EburyAppException e) {
	            
	        }
	        return "inicioUsuario.xhtml";
		}
		
		public String registrarIndividual() {
			
			try{
				individual.setTipoCliente("INDIVIDUAL");
				individual.setEstado("INACTIVO");
				Usuario aux = gestionUsuario.devolverUsuario(infoSesion.getUsuario().getUsuario());
				individual.setIdentificacion(aux.getUsuario());
				gestionCliente.registrarCliente(individual, aux.getId(), aux.getClave());
				return "vistaPrincipalCliente.xhtml";
			}catch(EburyAppException e){ 
				FacesMessage fm = new FacesMessage("Error: " + e);
	            FacesContext.getCurrentInstance().addMessage(null, fm);
			}
			return "unexpected.xhtml";
		}
		
		public String registrarAutorizado() {
			
			try{
				autorizado.setEstado("INACTIVO");
				Usuario aux = gestionUsuario.devolverUsuario(infoSesion.getUsuario().getUsuario());
				autorizado.setIdentificacion(aux.getUsuario());
				gestionAutorizado.registrarAutorizado(autorizado, aux.getId(), aux.getClave());
				return "vistaPrincipalCliente.xhtml";
			}catch(EburyAppException e){
				FacesMessage fm = new FacesMessage("Error: " + e);
	            FacesContext.getCurrentInstance().addMessage(null, fm);
			}
			return "unexpected.xhtml";
		}
		
		public String registrarEmpresa(){
			try{
				autorizado.setEstado("INACTIVO");
				gestionCliente.registrarEmpresa(empresa, autorizado.getId(), cuenta);
				return "vistaPrincipalCliente.xhtml";
			}catch(EburyAppException e){
				FacesMessage fm = new FacesMessage("Error: " + e);
	            FacesContext.getCurrentInstance().addMessage(null, fm);
			}
			return "unexpected.xhtml";
		}
		
		public String registroIndividual() {return "registroIndividual.xhtml";}
	       
	    public String registroAutorizado(){return "registroAutorizado.xhtml";}

	    public String registroEmpresa(){return "registroEmpresa.xhtml";}
       
} 