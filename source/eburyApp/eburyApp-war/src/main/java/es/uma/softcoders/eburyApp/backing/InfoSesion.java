package es.uma.softcoders.eburyApp.backing;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
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
        
        public synchronized void setUsuario(Usuario u){
                this.usuario = u;
        }

        public synchronized Usuario getUsuario(){
                return this.usuario;
        }

} 