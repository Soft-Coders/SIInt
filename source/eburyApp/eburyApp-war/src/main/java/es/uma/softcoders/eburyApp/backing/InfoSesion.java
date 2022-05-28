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
	

        private static int instance = 0;
        private int currentInstance;
        
        private Usuario usuario;
        

        public InfoSesion(){
        	System.out.println("> infoSesion.InfoSesion() : CREATED " + ++instance);
        	currentInstance = instance;
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
        	System.out.println("> infoSesion"+currentInstance+".setUsuario() : PRE : usuario");
                this.usuario = u;
            System.out.println("> infoSesion"+currentInstance+".setUsuario() : POST : usuario :\n" + usuario);
        }

        public synchronized Usuario getUsuario(){
                return this.usuario;
        }
        
        public boolean isAutorizado() {
        	System.out.println("> infoSesion"+currentInstance+".isAutorizado() : usuario :\n"+ usuario);
        	return usuario.isEsAdministrativo();
        }

} 