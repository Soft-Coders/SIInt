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
import es.uma.softcoders.eburyApp.exceptions.CuentaNoCoincidenteException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaNoEncontradaException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaNoEncontradaException;
import es.uma.softcoders.eburyApp.exceptions.UsuarioNoVinculadoException;

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
	
	@Test
	public void testAgregarAutorizado() {
		PersonaAutorizada pa2 = new PersonaAutorizada("ABC123", "Marta", "Maleno", "Calle Patata, 37");
		@SuppressWarnings("deprecation")
		Date cumple = new Date(2002,4,30);
		PersonaAutorizada pa3 = new PersonaAutorizada("ABC456", "Pablo", "Huertas", "Calle Boniato, 8", "ACTIVO",  cumple, new Date(), null);;
		Empresa em2 = new Empresa("Aldi");
		Empresa em4 = new Empresa("IKEA");
		em2.setID((long)9423903);
		try {
			gestionAutorizado.agregarAutorizado(pa2, em2.getID().toString(), 'L');
			gestionAutorizado.agregarAutorizado(pa3, em4.getID().toString(), 'O');
			assertNotNull(pa2.getAutorizacion());
			assertNotNull(pa3.getAutorizacion());
			assertNotNull(em2.getAutorizacion());
			assertNotNull(em4.getAutorizacion());
		} catch (EmpresaNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersonaAutorizadaExistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CuentaNoCoincidenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmpresaExistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UsuarioNoVinculadoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void EliminarAutorizado(){
		PersonaAutorizada pa2 = new PersonaAutorizada("ABC123", "Marta", "Maleno", "Calle Patata, 37");
		Empresa em2 = new Empresa("Aldi");
		try {
			gestionAutorizado.agregarAutorizado(pa2, em2.getID().toString(), 'L');
			gestionAutorizado.eliminarAutorizado(pa2.getId().toString(), em2.getID().toString());
		} catch (EmpresaNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersonaAutorizadaExistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CuentaNoCoincidenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmpresaExistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UsuarioNoVinculadoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersonaAutorizadaNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void BajarAutorizado(){
		PersonaAutorizada pa2 = new PersonaAutorizada("ABC123", "Marta", "Maleno", "Calle Patata, 37");
		try {
			gestionAutorizado.bajaAutorizado(pa2.getId().toString());
		} catch (PersonaAutorizadaNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
