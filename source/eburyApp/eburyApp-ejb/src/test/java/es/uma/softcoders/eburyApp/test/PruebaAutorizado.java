package es.uma.softcoders.eburyApp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Date;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Usuario;
import es.uma.softcoders.eburyApp.ejb.GestionAutorizado;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoCoincidenteException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaNoEncontradaException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaNoEncontradaException;
import es.uma.softcoders.eburyApp.exceptions.UsuarioNoVinculadoException;

//public class PruebaAutorizado {

	//private static final String AUTORIZADO_EJB = "java:global/classes/AutorizadoEJB";
	//private static final String UNIDAD_PERSITENCIA_PRUEBAS = "eburyAppTest";
	//private GestionAutorizado gestionAutorizado;
	/*
	@Before
	public void setup() throws NamingException, ParseException {
		gestionAutorizado = (GestionAutorizado) SuiteTest.ctx.lookup(AUTORIZADO_EJB);
		BaseDatosCT.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
*/
	
	/**
	 * Este test comprueba la funcionalidad del método agregarAutorizado()
	 */
	/*@Test
	public void pruebaAgregarAutorizado() {
		try {
			PersonaAutorizada pa0 =  new PersonaAutorizada();
			//gestionAutorizado.agregarAutorizado(123000L, pa0, 123L, 'L');
			//gestionAutorizado.agregarAutorizado(456000L, pa0, 456L, 'L');
		} catch (EmpresaNoEncontradaException e) {
			fail("Debería encontrar la empresa");
		} catch (PersonaAutorizadaExistenteException e) {
			fail("La persona autorizada ya está relacionada con la empresa");
		} catch (CuentaNoCoincidenteException e) {
			fail("No debería dar esta excepción");
		} catch (EmpresaExistenteException e) {
			fail("La empresa ya está relacionada con la persona autorizada");
		} catch (UsuarioNoVinculadoException e) {
			fail("Debe haber un usuario vinculado a la persona autorizada");
		}
	}
	
	@Test
	public void pruebaModificarAutorizado() {
		/*
		 * La aplicación permitirá a un administrativo modificar los datos de las 
		 * personas autorizadas a operar con cuentas de clientes que son personas jurídicas.
		 */
		/*PersonaAutorizada aux = new PersonaAutorizada("PERSONAAUTORIZADAAUX", "Persona", "Autorizada Aux", "Calle calle");
		try {
			gestionAutorizado.modificarAutorizado(aux, 123000L);
		} catch (PersonaAutorizadaNoEncontradaException e) {
			fail("Debería encontrar la persona autorizada");
		}
	}
	
	@Test
	public void pruebaBajaAutorizado() {
		try {
			gestionAutorizado.bajaAutorizado(123000L);
		} catch (PersonaAutorizadaNoEncontradaException e) {
			fail("No encuentra a la persona autorizada, pero debería");
		}
	}
	
	@Test
	public void pruebaEliminarAutorizado() {
		try {
			PersonaAutorizada pa0 =  new PersonaAutorizada();
			gestionAutorizado.agregarAutorizado(123000L, pa0, 123L, 'L');
			gestionAutorizado.eliminarAutorizado(123000L, 123L);
		} catch (PersonaAutorizadaNoEncontradaException e) {
			fail("No se ha encontrado a la persona autorizada");
		} catch (EmpresaNoEncontradaException e) {
			fail("No se ha encontrado a la empresa");
		} catch (PersonaAutorizadaExistenteException e) {
			fail("No debería dar este error");
		} catch (CuentaNoCoincidenteException e) {
			fail("No debería dar este error");
		} catch (EmpresaExistenteException e) {
			fail("No debería dar este error");
		} catch (UsuarioNoVinculadoException e) {
			fail("La persona autorizada no tiene ningún usuario vinculado");
		}
	}
	
	*/
	
	
	
	/**
	 * Este test comprueba que se pueda asociar una personaAutorizada a un Cliente de tipo Empresa
	 * para permitirle más adelante operar con sus las cuentasFintech.
	 * El test sirve para comprobar el Requisito RF6: Añadir autorizados a la cuenta de una persona jurídica
	 * Este test contempla los siguientes casos:
	 * <ul>
	 * <li>Agregación de Autorizados de Lectura</li>
	 * <li>Agregación de Autorizados de Operación</li>
	 * <li>Agregación de dos Autorizados en una misma empresa</li>
	 * <li>Agregación de Autorizados sin Usuario asociado</li>
	 * <li>Agregación de Autorizados sin ID</li>
	 * <li>Agregación de Autorizados a una Empresa sin ID</li>
	 * <li>Agregación de un Autorizado a dos Empresas distintas</li>
	 * <li>Agregación de Autorizados a una Empresa con un tipo de relación inválido (ni 'L' ni 'O')
	 * </ul>
	 * @author Marta Maleno
	 * */
	/*@Test
	@Requisitos({"RF6"})
	public void testAgregarAutorizado() {
		PersonaAutorizada pa2 = new PersonaAutorizada("ABC123", "Marta", "Maleno", "Calle Patata, 37");
		PersonaAutorizada pa3 = new PersonaAutorizada("ABC456", "Pablo", "Huertas", "Calle Boniato, 8");
		PersonaAutorizada pa4 = new PersonaAutorizada("ABC789", "Patata", "Tubérculo", "Calle calle, 10");
		PersonaAutorizada pa6 = new PersonaAutorizada("ABC012", "NombreA", "ApellidoA", "Calle acalle, 1");
		PersonaAutorizada pa7 = new PersonaAutorizada("ABC345", "NombreB", "ApellidoB", "Calle bcalle, 2");
		Empresa em2 = new Empresa("Aldi");
		Empresa em3 = new Empresa("EmpresaA");
		Empresa em4 = new Empresa("IKEA");
		Empresa em5 = new Empresa("EmpresaB");
		em2.setID((long)9423903);
		em4.setID((long)1467853);
		em5.setID((long)48712390);
		Usuario u1 = new Usuario("usuarioABC", "claveABC", false);
		Usuario u2 = new Usuario("usuarioDEF", "claveDEF", true);
		Usuario u3 = new Usuario("usuarioGHI", "claveGHI", false);
		Usuario u4 = new Usuario("usuarioJKL", "claveJKL", false);
		Usuario u5 = new Usuario("usuarioMNO", "claveMNO", false);
		pa2.setUsuario(u1);
		pa3.setUsuario(u2);
		pa4.setUsuario(u3);
		pa7.setUsuario(u5);

		try {
			gestionAutorizado.agregarAutorizado(pa2, em2.getID().toString(), 'L');
			gestionAutorizado.agregarAutorizado(pa3, em4.getID().toString(), 'O');
			assertNotNull(pa2.getAutorizacion());
			assertNotNull(pa3.getAutorizacion());
			assertNotNull(em2.getAutorizacion());
			assertNotNull(em4.getAutorizacion());
			gestionAutorizado.agregarAutorizado(pa4, em4.getID().toString(), 'O');
			assertNotNull(pa4.getAutorizacion());
			gestionAutorizado.agregarAutorizado(pa4, em2.getID().toString(), 'O');
			assertNotNull(pa4.getAutorizacion());
		} catch (EmpresaNoEncontradaException e) {
			e.printStackTrace();
		} catch (PersonaAutorizadaExistenteException e) {
			e.printStackTrace();
		} catch (CuentaNoCoincidenteException e) {
			e.printStackTrace();
		} catch (EmpresaExistenteException e) {
			e.printStackTrace();
		} catch (UsuarioNoVinculadoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			gestionAutorizado.agregarAutorizado(pa6, em2.getID().toString(), 'L');
			fail ("Debería lanzar una excepción porque la persona no tiene usuario");
		} catch(Exception e) {
			// ok
		}
		try {
			gestionAutorizado.agregarAutorizado(pa5, em2.getID().toString(), 'O');
			fail("Debería lanzar una excepción porque la persona no tiene ID");
		} catch (Exception e) {
			// ok
		}
		try {
			gestionAutorizado.agregarAutorizado(pa2, em3.getID().toString(), 'O');
			fail("Debería lanzar una excepción porque la empresa no tiene ID");
		} catch (Exception e) {
			// ok
		}
		try {
			gestionAutorizado.agregarAutorizado(pa7, em5.getID().toString(), 'X');
			fail ("Debería lanzar una excepción porque el tipo de la relación no es un carácter válido");
		} catch(Exception e) {
			//ok
		}
	}*/
	
	/**
	 * Este test comprueba que se cambie el estado de la relación entre una PersonaAutorizada y una
	 * Empresa a 'B' (B de Bloqueado) para guardar la información de que dicha PersonaAutorizada tuvo
	 * privilegios sobre las cuentas de la Empresa en el pasado aunque en el presente ya no los tenga.
	 * Además comprueba que se cambie el estado de una PersonaAutorizada a "INACTIVO" cuando
	 * no tiene ninguna Empresa asociada sobre la que pueda operar.
	 * El test sirve para comprobar el Requisito RF8: Eliminar autorizados de una cuenta
	 * @author Marta Maleno
	 * 
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
		em2.setCodigoPostal("29012");
		em2.setPais("España");
		Empresa em3 = new Empresa("Empresa1");
		em3.setID((long) 12337);
		em3.setIdentificacion("Empresa137");
		em3.setTipo_cliente("Empresa");
		em3.setEstado("ACTIVO");
		em3.setFecha_Alta(new Date());
		em3.setDireccion("Calle calle, 137");
		em3.setCiudad("Málaga");
		em3.setCodigoPostal("29011");
		em3.setPais("España");
		try {
			gestionAutorizado.agregarAutorizado(pa2, em2.getID().toString(), 'L');
			gestionAutorizado.agregarAutorizado(pa2, em3.getID().toString(), 'O');
			gestionAutorizado.eliminarAutorizado(pa2.getId().toString(), em2.getID().toString());
			assertNull(em2.getAutorizacion());
			assertNotNull(pa2.getAutorizacion());
			gestionAutorizado.eliminarAutorizado(pa2.getId().toString(), em3.getID().toString());
			assertNull(em3.getAutorizacion());
			assertNull(pa2.getAutorizacion());
			assertEquals(pa2.getEstado(), "INACTIVO");
		} catch (EmpresaNoEncontradaException e) {
			e.printStackTrace();
		} catch (PersonaAutorizadaExistenteException e) {
			e.printStackTrace();
		} catch (CuentaNoCoincidenteException e) {
			e.printStackTrace();
		} catch (EmpresaExistenteException e) {
			e.printStackTrace();
		} catch (UsuarioNoVinculadoException e) {
			e.printStackTrace();
		} catch (PersonaAutorizadaNoEncontradaException e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Este test comprueba que se puedan modificar los datos asociados a una PersonaAutorizada.
	 * El test sirve para comprobar el Requisito RF7: Modificación de datos de un autorizado
	 * Este test contempla los siguientes casos:
	 * <ul>
	 * <li>Modificación de Nombre de una PersonaAutorizada</li>
	 * <li>Modificación de Apellidos de una PersonaAutorizada</li>
	 * <li>Modificación de Dirección de una PersonaAutorizada</li>
	 * <li>Modificación de Estado de una PersonaAutorizada</li>
	 * </ul>
	 * @author Marta Maleno
	 * 
	@Test
	@Requisitos({"RF7"})
	public void ModificarAutorizado(){
		PersonaAutorizada pa2 = new PersonaAutorizada("ABCDEFG1237", "Marta", "Maleno", "Calle Patata, 37");
		PersonaAutorizada paaux = new PersonaAutorizada("ABCDEFG1237", null , null, "Calle calle, 37");
		PersonaAutorizada paaux0 = new PersonaAutorizada("ABCDEFG1237", "Pedro" , null, null);
		PersonaAutorizada paaux1 = new PersonaAutorizada("ABCDEFG1237", null , "Fernández", null);
		PersonaAutorizada paaux2 = new PersonaAutorizada("ABCDEFG1237", null , null , null, "ACTIVO", null, null, null);
		try {
			gestionAutorizado.modificarAutorizado(paaux, pa2.getId().toString());
			assertEquals(pa2.getDireccion(), paaux.getDireccion());
			gestionAutorizado.modificarAutorizado(paaux0, pa2.getId().toString());
			assertEquals(pa2.getNombre(), paaux0.getNombre());
			gestionAutorizado.modificarAutorizado(paaux1, pa2.getId().toString());
			assertEquals(pa2.getApellidos(), paaux1.getApellidos());
			gestionAutorizado.modificarAutorizado(paaux2, pa2.getId().toString());
			assertEquals(pa2.getApellidos(), paaux2.getEstado());
		} catch (PersonaAutorizadaNoEncontradaException e) {
			e.printStackTrace();
		}
	}*/

