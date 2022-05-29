package es.uma.softcoders.eburyApp.ejb;

import java.util.List;

import javax.ejb.Local;

import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Usuario;
import es.uma.softcoders.eburyApp.exceptions.UsuarioNoExistenteException;
import es.uma.softcoders.eburyApp.exceptions.UsuarioYaExistenteException;

@Local
public interface GestionUsuario {
	
	/** Crea un usuario con el atributo es_administrativo = false
	 * 
	 * @param username el usuario
	 * @throws UsuarioYaExistenteException
	 * @author Pablo Huertas
	*/
	public void agregarUsuario(String username, String password) throws UsuarioYaExistenteException;
	
	/** Crea un usuario con el atributo es_administrativo = true
	 * 
	 * @param 
	 * @throws UsuarioYaExistenteException
	 * @author Pablo Huertas
	*/
	
	public List<Usuario> devolverUsuario(String user);
	
	public void agregarAdministrativo( Long id,String username, String password) throws UsuarioYaExistenteException;
	
	/**Añade un cliente individual al usuario
	 * 
	 * @param 
	 * @throws UsuarioNoExistenteException
	 * @author Pablo Huertas
	*/
	public void agregarIndividual(Long id, Individual individual) throws UsuarioNoExistenteException;
	
	/** Añade una persona autorizada al usuario
	 * 
	 * @param 
	 * @throws UsuarioNoExistenteException
	 * @author Pablo Huertas
	*/
	public void agregarAutorizado(Long id, PersonaAutorizada autorizado) throws UsuarioNoExistenteException;
	
	/** Convierte un usuario normal en autorizado
	 * 
	 * @param 
	 * @throws UsuarioNoExistenteException
	 * @author Pablo Huertas
	*/
	public void convertirAdministrativo(Long id) throws UsuarioNoExistenteException;
	public Usuario devolverUser(String user);
}
