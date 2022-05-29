package es.uma.softcoders.eburyApp.backing;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Usuario;
import es.uma.softcoders.eburyApp.ejb.GestionUsuario;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;

import javax.inject.Inject;

@Named(value="infoSesion")
@SessionScoped
public class InfoSesion implements Serializable{
	
        @EJB 
        private GestionUsuario gestionUsuario;
        
        private Usuario usuario = null;
        
        private Individual individual = null;
        
        private PersonaAutorizada autorizado = null;
        
        private Empresa empresa = null;

        public InfoSesion(){

        }
        
        public synchronized String mainCliente() {
        	
        		if(gestionUsuario.devolverUsuario(usuario.getUsuario()).get(0).getIndividual()==null) {
            		return "registroIndividual.xhtml";
            	}else {
            		individual = gestionUsuario.devolverUsuario(usuario.getUsuario()).get(0).getIndividual();
                	return "vistaPrincipalCliente.xhtml";
            	}

        	
        }
        
        public synchronized String mainAutorizado() {
        	if(gestionUsuario.devolverUsuario(usuario.getUsuario()).get(0).getPersonaAutorizada()==null) {
        		return "registroAutorizado.xhtml";
        	}else {
        		autorizado = gestionUsuario.devolverUsuario(usuario.getUsuario()).get(0).getPersonaAutorizada();
            	return "vistaPrincipalAutorizado.xhtml";
        	}
        }
        

        public synchronized String invalidarSesion(){
                if (usuario != null)
                {
                        usuario = null;
                        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                }
                return "index.xhtml";
        }
        public synchronized String invalidarSesionAdmin(){
                if (usuario != null)
                {
                        usuario = null;
                        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                }
                return "admin.xhtml";
        }
        
        public synchronized void setUsuario(Usuario u){
                this.usuario = u;
        }

        public synchronized Usuario getUsuario(){
                return this.usuario;
        }

		public Individual getIndividual() {
			return individual;
		}

		public void setIndividual(Individual individual) {
			this.individual = individual;
		}

		public PersonaAutorizada getAutorizado() {
			return autorizado;
		}

		public void setAutorizado(PersonaAutorizada autorizado) {
			this.autorizado = autorizado;
		}

		public Empresa getEmpresa() {
			return empresa;
		}

		public void setEmpresa(Empresa empresa) {
			this.empresa = empresa;
		}
        


        //Lo he comentado, ya que la comprobación la hago en el propio .xhmtl
        /*public synchronized String hayCliente(){
                if(usuario.getIndividual()==null){
                        return "registro.xhtml";
                }else{
                        return "vistaPrincipalCliente.xhtml";
                }
        }
        
        public synchronized String hayAutorizado(){
                if(usuario.getPersonaAutorizada()==null){
                        return "registro.xhtml";
                }else{
                        return "vistaPrincipalAutorizado.xhtml";
                }
        }*/

} 