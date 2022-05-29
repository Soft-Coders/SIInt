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

@Named(value="userParam")
@SessionScoped
public class UserParam implements Serializable{
	
        private String user;

        public void setUser(String u){
                this.user = u;
        }
        
        public String getUser(){
                return this.user;
        }
} 