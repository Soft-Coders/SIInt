package es.uma.softcoders.eburyApp.backing;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import es.uma.softcoders.eburyApp.Usuario;
import es.uma.softcoders.eburyApp.ejb.GestionLogin;
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
		
		@Inject 
		private InfoSesion sesion;
		
		private Usuario usuario;

		public Login() {
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
	            login.loginUsuario(usuario.getUsuario(), usuario.getClave());
	            sesion.setUsuario(usuario);
	            return "inicioUsuario.xhtml" + "?faces-redirect=true&idsession=" + usuario.getUsuario();

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

		public boolean esAdministrativo(String id){
			try {
				return login.comprobacionAdministrativo(id);
			} catch (EburyAppException e) {
				e.printStackTrace();
			}
			return false;
			

		}

		public String entrarAdmin(){
			try {
	            login.loginAdmin(usuario.getUsuario(), usuario.getClave());
				usuario.setEsAdministrativo(true);
	            sesion.setUsuario(usuario);
	            return "inicioAdmin.xhtml" + "?faces-redirect=true&idsession=" + usuario.getUsuario();

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