package es.uma.softcoders.eburyApp.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Usuario;
import es.uma.softcoders.eburyApp.exceptions.UsuarioNoExistenteException;
import es.uma.softcoders.eburyApp.exceptions.UsuarioYaExistenteException;

@Stateless
public class UsuarioEJB implements GestionUsuario{
	
	@PersistenceContext(unitName="eburyAppEjb")
	private EntityManager em;
	
	public UsuarioEJB() {
		
	}
	
	public void agregarUsuario(String nUsuario, String password) throws UsuarioYaExistenteException{
		Usuario usuarioNuevo = new Usuario();
		usuarioNuevo.setClave(password);
		usuarioNuevo.setEsAdministrativo(false);
		usuarioNuevo.setUsuario(nUsuario);
		
		em.persist(usuarioNuevo);
		
		
	}
	
	public List<Usuario> devolverUsuario(String user) {
		Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario LIKE :uname ");
		q.setParameter("uname", user);
		List<Usuario> aux = q.getResultList();
		return aux;
	}
	
	
	public void agregarAdministrativo(Long user,String nUsuario, String password)throws UsuarioYaExistenteException{
		Usuario usuario = em.find(Usuario.class, user);
		if(usuario != null) {
			throw new UsuarioYaExistenteException("El usuario ya existe");
		}
		Usuario usuarioNuevo = new Usuario();
		usuarioNuevo.setClave(password);
		usuarioNuevo.setEsAdministrativo(true);
		usuarioNuevo.setUsuario(nUsuario);
		
		em.persist(usuarioNuevo);
	}
	
	public void agregarIndividual(Long user, Individual individual) throws UsuarioNoExistenteException{
		Usuario usuario = em.find(Usuario.class, user);
		if(usuario == null) {
			throw new UsuarioNoExistenteException("El usuario no existe");
		}
		
		usuario.setIndividual(individual);
		
		em.merge(usuario);
	}
	
	public void agregarAutorizado(Long user, PersonaAutorizada autorizado) throws UsuarioNoExistenteException{
		Usuario usuario = em.find(Usuario.class, user);
		if(usuario == null) {
			throw new UsuarioNoExistenteException("El usuario no existe");
		}
		
		usuario.setPersonaAutorizada(autorizado);
		
		em.merge(usuario);
	}
	
	public void convertirAdministrativo(Long user) throws UsuarioNoExistenteException{
		Usuario usuario = em.find(Usuario.class, user);
		if(usuario == null) {
			throw new UsuarioNoExistenteException("El usuario no existe");
		}
		
		usuario.setEsAdministrativo(true);
		
		em.merge(usuario);
		
	}
}
