package es.uma.softcoders.eburyApp.test;

import static org.junit.Assert.fail;

import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.uma.softcoders.eburyApp.ejb.GestionLogin;
import es.uma.softcoders.eburyApp.ejb.LoginEJB;
import es.uma.softcoders.eburyApp.exceptions.ClienteNoEncontradoException;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoCoincidenteException;

import es.uma.softcoders.eburyApp.exceptions.UsuarioNoAdministrativoException;

public class TestLogin {
	private static final String LOGIN_EJB = "java:global/classes/LoginEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "eburyAppTest";
	
	private GestionLogin gestionLogin;
	private final String uNoAdmin = "noadmin";
	private final String uAdmin   = "admin";
	
	
	@Before
	public void setup() throws NamingException  {
		gestionLogin = (GestionLogin) new LoginEJB();
		BaseDatosLogin.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
//	@After
//	public void terminate() {
//		BaseDatosLogin.dropBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
//	}
	
	@Test
	public void testLoginAdmin() {
		
		
		// Usuario no existente
		try {
			gestionLogin.loginAdmin("UsuarioInexistente", "clave");
		} catch(ClienteNoEncontradoException e) {
			//OK
		} catch(Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Usuario no admin
		try {
			gestionLogin.loginAdmin(uNoAdmin, uNoAdmin);
		} catch(UsuarioNoAdministrativoException e) {
			//OK
		} catch(Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Clave incorrecta
		try {
			gestionLogin.loginAdmin(uAdmin, "contraseniaRandom");
		} catch(CuentaNoCoincidenteException e) {
			//OK
		} catch(Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Usuario admin Clave correcta
		try {
			gestionLogin.loginAdmin(uAdmin, uAdmin);
		} catch(Exception e) {
			fail("No debería lanzar ningnua excepción: " + e.getMessage() + "-" + e.getClass());
		}
	}
	
	@Test
	public void testLoginUsuario() {
		// Usuario no existente
		try {
			gestionLogin.loginUsuario("UsuarioInexistente", "clave");
		} catch(ClienteNoEncontradoException e) {
			//OK
		} catch(Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Clave incorrecta
		try {
			gestionLogin.loginUsuario(uNoAdmin, "contraseniaRandom");
		} catch(CuentaNoCoincidenteException e) {
			//OK
		} catch(Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Usuario y clave correctos
		try {
			gestionLogin.loginUsuario(uNoAdmin, uNoAdmin);
		} catch(Exception e) {
			fail("No debería lanzar ninguna excepción: " + e.getMessage() + "-" + e.getClass());
		}
	}
	
}
	

