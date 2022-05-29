package es.uma.softcoders.eburyApp.backing;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import es.uma.softcoders.eburyApp.Usuario;
import es.uma.softcoders.eburyApp.ejb.GestionLogin;
import es.uma.softcoders.eburyApp.ejb.GestionUsuario;
import es.uma.softcoders.eburyApp.ejb.LoginEJB;
import es.uma.softcoders.eburyApp.exceptions.ClienteNoEncontradoException;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoCoincidenteException;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;

import javax.inject.Inject;

@Named(value="login")
@RequestScoped
public class Login implements Serializable{
	
		@EJB
		private GestionLogin login;
		
		@EJB
		private GestionUsuario gestionUsuario;
		
		@Inject 
		private InfoSesion sesion;
		 
        @Inject 
    	private CuentaBB cuentaBB;
		
		private Usuario usuario;
		private static int instance = 0;
		
		public Login() {
			System.out.println("> login.Login() : CREATED : " + ++instance);
			usuario = new Usuario();
		}
		
		public Usuario getUsuario() {
			return usuario;
		}
		
		public void setUsuario(Usuario usuario) {
	        this.usuario = usuario;
	    }
		
		
		
		public String entrar() {
			try {
				System.out.println("> login.entrar() : PRE : loginUsuario()");
	            login.loginUsuario(usuario.getUsuario(), usuario.getClave());
	            System.out.println("> login.entrar() : POST : loginUsuario() : PRE : setUsuario()");
	            sesion.setUsuario(usuario);
	            cuentaBB.setUsuario(gestionUsuario.devolverUser(usuario.getUsuario()).toString());
                
	            System.out.println("> login.entrar() : POST : setUsuario()");

	            return "inicioUsuario.xhtml";

	        } catch (ClienteNoEncontradoException e) {
	            FacesMessage fm = new FacesMessage("La cuenta no existe");
	            FacesContext.getCurrentInstance().addMessage("login:user", fm);
	        } catch (CuentaNoCoincidenteException e) {
	            FacesMessage fm = new FacesMessage("La contraseña no es correcta");
	            FacesContext.getCurrentInstance().addMessage("login:pass", fm);
	        } catch (EburyAppException e) {
	            FacesMessage fm = new FacesMessage("Error: " + e);
	            FacesContext.getCurrentInstance().addMessage(null, fm);
	        }
	        return null;
		}

		public String entrarAdmin(){
			try {
				System.out.println("> login.entrarAdmin() : PRE : loginAdmin()");
	            login.loginAdmin(usuario.getUsuario(), usuario.getClave());
	            System.out.println("> login.entrarAdmin() : POST : loginAdmin() : PRE : setUsuario()");
				usuario.setEsAdministrativo(true);
	            sesion.setUsuario(usuario);
	            System.out.println("> login.entrarAdmin() : POST : setUsuario()");
	            return "inicioAdmin.xhtml";

	        } catch (ClienteNoEncontradoException e) {
	            FacesMessage fm = new FacesMessage("La cuenta no existe");
	            FacesContext.getCurrentInstance().addMessage("login:user", fm);
	        } catch (CuentaNoCoincidenteException e) {
	            FacesMessage fm = new FacesMessage("La contraseña no es correcta");
	            FacesContext.getCurrentInstance().addMessage("login:pass", fm);
	        } catch (EburyAppException e) {
	            FacesMessage fm = new FacesMessage("Error: " + e);
	            FacesContext.getCurrentInstance().addMessage(null, fm);
	        }
	        return null;
		}

} 