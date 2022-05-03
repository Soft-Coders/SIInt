package es.uma.softcoders.eburyApp.test;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.Pooled;
import es.uma.softcoders.eburyApp.Segregada;
import es.uma.softcoders.eburyApp.ejb.GestionCuenta;
import es.uma.softcoders.eburyApp.exceptions.CuentaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoExistenteException;
import es.uma.softcoders.eburyApp.exceptions.DatosIncorrectosException;

public class PruebaCuenta {
	private static final String CUENTA_EJB = "java:global/classes/CuentaEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "eburyAppTest";
	
	private GestionCuenta gestionCuenta;	
	
	@Before
	public void setup() throws NamingException  {
		gestionCuenta = (GestionCuenta) SuiteTest.ctx.lookup(CUENTA_EJB);
		BaseDatosCuenta.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	/**Este test se encarga de comprobar que la <b>creación</b> de <b>CuentasFintech</b> se lleve a cabo de la forma esperada.
	 * Tanto en casos válidos, que opere de la manera esperada; como en casos inválidos, que los reconozca y reaccione en consecuencia.
	 * Este test contempla los siguientes casos:
	 * <ul>
	 * <li>Creación de Cuenta ya existente</li>
	 * <li>Creación de Cuenta con IBAN null</li>
	 * <li>Creación de Cuenta con Cliente null</li>
	 * <li>Creación de Cuenta con Cliente pre-existente</li>
	 * <li>Creación de Cuenta Pooled ideal</li>
	 * <li>Creación de Cuenta Segragada ideal</li>
	 * </ul>
	 * 
	 * @author Ignacio Lopezosa
	 * */
	@Test
	@Requisitos({"RF5"})
	public void testCrearCuentaFintech() {
		
		final String CFPE = "cfPreExistente-22";
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		
		CuentaFintech cf = new Segregada();
		Cliente cliente = new Cliente("0000", "tipo", "ACTIVO", new Date(), "Calle calle, 1", "ciudad", "29620", "pais");
		cliente.setID(Long.valueOf(0122));
		try {
			cf.setCliente(cliente);
			cf.setEstado("ACTIVO");
			cf.setFechaApertura(date.parse("2019-09-19"));
			cf.setIBAN("cfIdeal-22");
			cf.setSwift("Swift");
		}catch (ParseException e) {
			throw new RuntimeException(e);
		}
		
		// Cuenta PreExistente
		try {
			cf.setIban(CFPE);
			gestionCuenta.crearCuentaFintech(cf);
		}catch(CuentaExistenteException e) {
			// OK
		}catch (Exception e) {
			fail("No debería lanzar esta excepción-1: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Iban null
		try {
			cf.setIban(null);
			gestionCuenta.crearCuentaFintech(cf);
		}catch (DatosIncorrectosException e){
			//OK
		}catch(Exception e) {
			fail("No debería lanzar esta excepción-2: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Cliente null
		try {
			cf.setIBAN("cfIdeal-22");
			cf.setCliente(null);
			gestionCuenta.crearCuentaFintech(cf);
		}catch (DatosIncorrectosException e){
			//OK
		}catch(Exception e) {
			fail("No debería lanzar esta excepción-3: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Cliente PreExistente
		try {
			cliente.setID(Long.valueOf(0022));
			cf.setCliente(cliente);
			gestionCuenta.crearCuentaFintech(cf);
		}catch (DatosIncorrectosException e){
			//OK
		}catch(Exception e) {
			fail("No debería lanzar esta excepción-4: " + e.getMessage() + "-" + e.getClass());
		}
		
		CuentaFintech cfIdealPooled = new Pooled();
		CuentaFintech cfIdealSegregada = new Segregada();
		cliente.setID(Long.valueOf(0122));
		try {
			cfIdealPooled.setCliente(cliente);
			cfIdealPooled.setEstado("ACTIVO");
			cfIdealPooled.setFechaApertura(date.parse("2019-09-19"));
			cfIdealPooled.setIBAN("cfIdealPooled-2-22");
			cfIdealPooled.setSwift("Swift");
			
			cfIdealSegregada.setCliente(cliente);
			cfIdealSegregada.setEstado("ACTIVO");
			cfIdealSegregada.setFechaApertura(date.parse("2019-09-19"));
			cfIdealSegregada.setIBAN("cfIdealSegregada-2-22");
			cfIdealSegregada.setSwift("Swift");
		}catch(ParseException e) {
			throw new RuntimeException(e);
		}
		
		// Cuenta Pooled Ideal
		try {
			gestionCuenta.crearCuentaFintech(cfIdealPooled);
		}catch(Exception e) {
			fail("No debería lanzar ninguna excepción-5: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Cuenta Segregada Ideal
		try {
			gestionCuenta.crearCuentaFintech(cfIdealSegregada);
		}catch(Exception e) {
			fail("No debería lanzar ninguna excepción-6: " + e.getMessage() + "-" + e.getClass());
		}
	}
	
	/**Este test se encarga de comprobar que la <b>baja</b> de <b>CuentasFintech</b> se lleve a cabo de la forma esperada.
	 * Tanto en casos válidos, que opere de la manera esperada; como en casos inválidos, que los reconozca y reaccione en consecuencia.
	 * Este test contempla los siguientes casos:
	 * <ul>
	 * <li>Cerrar Cuenta no existente</li>
	 * <li>Cerrar Cuenta ya cerrada</li>
	 * <li>Cerrar una Cuenta Pooled Ideal (Activa y existente)</li>
	 * <li>Cerrar una Cuenta Segregada Ideal (Activa y existente)</li>
	 * </ul>
	 * 
	 * @author Ignacio Lopezosa
	 * */
	@Test
	@Requisitos({"RF9"})
	public void testCerrarCuentaFintech() {

		final String CFCI = "cfCuentaInactiva-22";
		final String CFIP = "cfIdealPooled-22";
		final String CFIS = "cfIdealSegregada-22";
		
		// Cuenta no existente
		try {
			gestionCuenta.cerrarCuentaFintech("IBANRandomQueNoExisteEnLaBD-22");
		} catch(CuentaNoExistenteException e) {
			//OK
		} catch(Exception e) {
			fail("No debería lanzar esta excepción-1:" + e.getMessage() + "-" + e.getClass());
		}
		
		// Cuenta Inactiva
		try {
			gestionCuenta.cerrarCuentaFintech(CFCI);
		} catch(CuentaNoExistenteException e) {
			//OK
		} catch(Exception e) {
			fail("No debería lanzar esta excepción-2:" + e.getMessage() + "-" + e.getClass());
		}
		
		// Cuenta Pooled Ideal
		try {
			gestionCuenta.cerrarCuentaFintech(CFIP);
		} catch(Exception e) {
			fail("No debería lanzar ninguna excepción-3:" + e.getMessage() + "-" + e.getClass());
		}
		
		// Cuenta Segregada Ideal
		try {
			gestionCuenta.cerrarCuentaFintech(CFIS);
		} catch(Exception e) {
			fail("No debería lanzar ninguna excepción-4:" + e.getMessage() + "-" + e.getClass());
		}
	}
	
}
	

