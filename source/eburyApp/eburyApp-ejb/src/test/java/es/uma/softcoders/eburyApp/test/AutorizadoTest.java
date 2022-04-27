package es.uma.softcoders.eburyApp.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.PersonaAutorizada;

public class AutorizadoTest {
	
	// Agregar, modificar, eliminar y dar de baja un autorizado
	
	// @Before ???
	
	// asserts: Equals, Null, NotNull, True
	// fail(mensajito)
	
	@Test
	public void testCrearAutorizado() {
		PersonaAutorizada pa = new PersonaAutorizada();
		assertNotNull(pa);
	}
	
	@Test
	public void testAgregarAutorizado() {
		PersonaAutorizada pa = new PersonaAutorizada();
		Empresa emp = new Empresa();
		
		
	}
	
}
