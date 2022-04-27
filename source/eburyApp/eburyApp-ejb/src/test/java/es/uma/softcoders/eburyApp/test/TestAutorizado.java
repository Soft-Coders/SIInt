package es.uma.softcoders.eburyApp.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.naming.NamingException;
import org.junit.Before;
import org.junit.Test;

import es.uma.softcoders.eburyApp.Empresa;
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
	public void testCrearPersonasAutorizadas() {
		try {
			PersonaAutorizada pa1 = new PersonaAutorizada();
			PersonaAutorizada pa2 = new PersonaAutorizada("ABC123", "Marta", "Maleno", "Calle Patata, 37");
			Date cumple = new Date(2002,4,30);
			PersonaAutorizada pa3 = new PersonaAutorizada("ABC456", "Pablo", "Huertas", "Calle Boniato, 8", "ACTIVO",  cumple, new Date(), null);
			assertNotNull(pa1);
			assertNotNull(pa2);
			assertNotNull(pa3);
		} catch (Exception e) {
			fail("No debería lanzar esta excepción");
		}
	}
	
	@Test
	public void testCrearEmpresa() {
		try {
			Empresa em1 = new Empresa();
			Empresa em2 = new Empresa("Aldi");
			assertNotNull(em1);
			assertNotNull(em2);
		}catch (Exception e) {
			fail("No debería lanzar esta excepción");
		}
	}
	
	
}
