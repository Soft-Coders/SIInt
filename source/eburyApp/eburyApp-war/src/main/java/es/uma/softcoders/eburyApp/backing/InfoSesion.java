package es.uma.softcoders.eburyApp.backing;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import es.uma.softcoders.eburyApp.Usuario;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;

import javax.inject.Inject;

@Named(value="infoSesion")
@RequestScoped
public class InfoSesion implements Serializable{
	
        
        
        private Usuario usuario;

        public InfoSesion(){

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

} 