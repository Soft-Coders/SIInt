package es.uma.softcoders.eburyApp.test;

import static org.junit.Assert.fail;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.softcoders.eburyApp.ejb.GestionLogin;
import es.uma.softcoders.eburyApp.ejb.LoginEJB;
import es.uma.softcoders.eburyApp.exceptions.ClienteNoEncontradoException;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoCoincidenteException;

import es.uma.softcoders.eburyApp.exceptions.UsuarioNoAdministrativoException;

public class PruebaLogin {
	private static final String LOGIN_EJB = "java:global/classes/LoginEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "eburyAppTest";
	
	private GestionLogin gestionLogin;
	private final String uNoAdmin = "noadmin-22";
	private final String uAdmin   = "admin-22";
	
	
	@Before
	public void setup() throws NamingException  {
		gestionLogin = (GestionLogin) SuiteTest.ctx.lookup(LOGIN_EJB);
		BaseDatosLogin.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
//	@After
//	public void terminate() {
//		BaseDatosLogin.dropBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
//	}
	
	/**Este test se encarga de comprobar que el login proveido para usuarios <b>administrativos</b> sean capaces de realizar las operaciones indicadas.
	 * Tanto en casos válidos, que opere de la manera esperada; como en casos inválidos, que los reconozca y reaccione en consecuencia.
	 * Este test contempla los siguientes casos:
	 * <ul>
	 * <li>Login de un usuario inexistente</li>
	 * <li>Login de un usuario no administrativo</li>
	 * <li>Login con clave incorrecta</li>
	 * <li>Login con usuario administrativo y clave correcta (caso ideal)</li>
	 * </ul>
	 * 
	 * @author Ignacio Lopezosa
	 * */
	@Test
	@Requisitos({"RF1"})
	public void testLoginAdmin() {
		
		
		// Usuario no existente
		try {
			gestionLogin.loginAdmin("UsuarioInexistente", "clave");
		} catch(ClienteNoEncontradoException e) {
			//OK
		} catch(Exception e) {
			fail("No debería lanzar esta excepción-1: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Usuario no admin
		try {
			gestionLogin.loginAdmin(uNoAdmin, uNoAdmin);
		} catch(UsuarioNoAdministrativoException e) {
			//OK
		} catch(Exception e) {
			fail("No debería lanzar esta excepción-2: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Clave incorrecta
		try {
			gestionLogin.loginAdmin(uAdmin, "contraseniaRandom");
		} catch(CuentaNoCoincidenteException e) {
			//OK
		} catch(Exception e) {
			fail("No debería lanzar esta excepción-3: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Usuario admin Clave correcta
		try {
			gestionLogin.loginAdmin(uAdmin, uAdmin);
		} catch(Exception e) {
			fail("No debería lanzar ningnua excepción-4: " + e.getMessage() + "-" + e.getClass());
		}
	}
	
	/**Este test se encarga de comprobar que el login proveido para usuarios <b>clientes y autorizados</b> sean capaces de realizar las operaciones indicadas.
	 * Tanto en casos válidos, que opere de la manera esperada; como en casos inválidos, que los reconozca y reaccione en consecuencia.
	 * Este test contempla los siguientes casos:
	 * <ul>
	 * <li>Login de un usuario inexistente</li>
	 * <li>Login con clave incorrecta</li>
	 * <li>Login con usuario no administrativo y clave correcta (caso ideal)</li>
	 * </ul>
	 * 
	 * @author Ignacio Lopezosa
	 * */
	@Test
	@Requisitos({"RF10"})
	public void testLoginUsuario() {
		// Usuario no existente
		try {
			gestionLogin.loginUsuario("UsuarioInexistente", "clave");
		} catch(ClienteNoEncontradoException e) {
			//OK
		} catch(Exception e) {
			fail("No debería lanzar esta excepción-1: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Clave incorrecta
		try {
			gestionLogin.loginUsuario(uNoAdmin, "contraseniaRandom");
		} catch(CuentaNoCoincidenteException e) {
			//OK
		} catch(Exception e) {
			fail("No debería lanzar esta excepción-2: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Usuario y clave correctos
		try {
			gestionLogin.loginUsuario(uNoAdmin, uNoAdmin);
		} catch(Exception e) {
			fail("No debería lanzar ninguna excepción-3: " + e.getMessage() + "-" + e.getClass());
		}
	}
	
}
	

