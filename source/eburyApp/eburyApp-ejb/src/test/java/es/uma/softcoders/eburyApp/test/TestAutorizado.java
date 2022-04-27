package es.uma.softcoders.eburyApp.test;

import static org.junit.Assert.fail;

import javax.naming.NamingException;
import org.junit.Before;
import org.junit.Test;

import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.ejb.GestionAutorizado;

public class TestAutorizado {

	private static final String AUTORIZADO_EJB = "java:global/classes/AutorizadoEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "eburyAppTest";
	private GestionAutorizado gestionAutorizado;
	
	@Before
	public void setup() throws NamingException{
		gestionAutorizado = (GestionAutorizado) SuiteTest.ctx.lookup(AUTORIZADO_EJB);
		BaseDatosAutorizado.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	@Test
	public void testAutorizadosCreados() {
		try {
			
		} catch (Exception e) {
			fail("No debería lanzar esta excepción");
		}
		
	}
	
	
}
