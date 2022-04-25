package es.uma.softcoders.eburyApp.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.softcoders.eburyApp.Usuario;


@Stateless
public class LoginEJB implements GestionLogin {
	
	@PersistenceContext(name="eburyAppEjb")
	private EntityManager em;
	
	public void loginAdmin(String cuenta, String clave){
		Usuario u = em.find(Usuario.class, cuenta);

		if(u == null){
			throw new UsuarioNoEncontradoException("Usuario no encontrado");
		}

		if(u.getClave() != clave){
			throw new ClaveIncorrectaException("");
		}

	}
	
	
}