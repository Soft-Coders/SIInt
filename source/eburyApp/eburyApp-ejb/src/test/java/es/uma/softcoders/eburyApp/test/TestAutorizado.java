package es.uma.softcoders.eburyApp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.naming.NamingException;
import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.ejb.AutorizadoEJB;
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
		//gestionAutorizado = (GestionAutorizado) SuiteTest.ctx.lookup(AUTORIZADO_EJB);
		gestionAutorizado = (GestionAutorizado) new AutorizadoEJB();
		BaseDatosAutorizado.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	@Test
	public void testCrearPersonasAutorizadas() {
		try {
			PersonaAutorizada pa2 = new PersonaAutorizada("ABCDEF123", "Marta", "Maleno", "Calle Patata, 37");
			@SuppressWarnings("deprecation")
			Date cumple = new Date(2002,4,30);
			PersonaAutorizada pa3 = new PersonaAutorizada("ABCDEF456", "Pablo", "Huertas", "Calle Boniato, 8", "ACTIVO",  cumple, new Date(), null);
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
	@Requisitos({"RF6"})
	public void testAgregarAutorizado() {
		PersonaAutorizada pa2 = new PersonaAutorizada("ABC123", "Marta", "Maleno", "Calle Patata, 37");
		@SuppressWarnings("deprecation")
		Date cumple = new Date(2002,4,30);
		PersonaAutorizada pa3 = new PersonaAutorizada("ABC456", "Pablo", "Huertas", "Calle Boniato, 8", "ACTIVO",  cumple, new Date(), null);;
		Empresa em2 = new Empresa("Aldi");
		Empresa em4 = new Empresa("IKEA");
		em2.setID((long)9423903);
		em4.setID((long)1467853);
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
	@Requisitos({"RF8"})
	public void EliminarAutorizado(){
		PersonaAutorizada pa2 = new PersonaAutorizada("ABCDE123", "Marta", "Maleno", "Calle Patata, 37");
		Empresa em2 = new Empresa("Aldi");
		em2.setID((long) 12337);
		em2.setIdentificacion("Aldi37");
		em2.setTipo_cliente("Empresa");
		em2.setEstado("ACTIVO");
		em2.setFecha_Alta(new Date());
		em2.setDireccion("Calle calle, 237");
		em2.setCiudad("Málaga");
		em2.setCodigoPostal(29012);
		em2.setPais("España");
		Empresa em3 = new Empresa("Empresa1");
		em3.setID((long) 12337);
		em3.setIdentificacion("Empresa137");
		em3.setTipo_cliente("Empresa");
		em3.setEstado("ACTIVO");
		em3.setFecha_Alta(new Date());
		em3.setDireccion("Calle calle, 137");
		em3.setCiudad("Málaga");
		em3.setCodigoPostal(29011);
		em3.setPais("España");
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
	@Requisitos({"RF8"})
	public void BajarAutorizado(){
		PersonaAutorizada pa2 = new PersonaAutorizada("ABCD1237", "Marta", "Maleno", "Calle Patata, 37");
		try {
			gestionAutorizado.bajaAutorizado(pa2.getId().toString());
			assertEquals(pa2.getEstado(), "INACTIVO");
		} catch (PersonaAutorizadaNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@Requisitos({"RF7"})
	public void ModificarAutorizado(){
		PersonaAutorizada pa2 = new PersonaAutorizada("ABCDEFG1237", "Marta", "Maleno", "Calle Patata, 37");
		PersonaAutorizada paaux = new PersonaAutorizada("ABCDEFG1237", null , null, "Calle calle, 37");
		PersonaAutorizada paaux0 = new PersonaAutorizada("ABCDEFG1237", "Pedro" , null, null);
		PersonaAutorizada paaux1 = new PersonaAutorizada("ABCDEFG1237", null , "Fernández", null);
		try {
			gestionAutorizado.modificarAutorizado(paaux, pa2.getId().toString());
			assertEquals(pa2.getDireccion(), paaux.getDireccion());
			gestionAutorizado.modificarAutorizado(paaux0, pa2.getId().toString());
			assertEquals(pa2.getNombre(), paaux.getNombre());
			gestionAutorizado.modificarAutorizado(paaux1, pa2.getId().toString());
			assertEquals(pa2.getApellidos(), paaux.getApellidos());
		} catch (PersonaAutorizadaNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
