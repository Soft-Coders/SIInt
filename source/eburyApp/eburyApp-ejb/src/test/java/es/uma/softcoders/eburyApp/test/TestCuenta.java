package es.uma.softcoders.eburyApp.test;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.Pooled;
import es.uma.softcoders.eburyApp.Segregada;
import es.uma.softcoders.eburyApp.ejb.CuentaEJB;
import es.uma.softcoders.eburyApp.ejb.GestionCuenta;
import es.uma.softcoders.eburyApp.exceptions.CuentaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoExistenteException;
import es.uma.softcoders.eburyApp.exceptions.DatosIncorrectosException;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;

public class TestCuenta {
	private static final String CUENTA_EJB = "java:global/classes/CuentaEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "eburyAppTest";
	
	private GestionCuenta gestionCuenta;
	
	
	@Before
	public void setup() throws NamingException  {
		gestionCuenta = (GestionCuenta) new CuentaEJB();
		BaseDatosCuenta.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
//	@After
//	public void terminate() {
//		BaseDatosCuenta.dropBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
//	}
	
	@Test
	public void testCrearCuentaFintech() {
		
		final String CFPE = "cfPreExistente";
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		
		CuentaFintech cf = new Segregada();
		Cliente cliente = new Cliente("0000", "tipo", "ACTIVO", new Date(), "Calle calle, 1", "ciudad", "29620", "pais");
		cliente.setID(Long.valueOf(1));
		try {
			cf.setCliente(cliente);
			cf.setEstado("ACTIVO");
			cf.setFechaApertura(date.parse("2019-09-19"));
			cf.setIBAN("cfIdeal");
			cf.setSwift("Swift");
		}catch (ParseException e) {
			throw new RuntimeException(e);
		}
		
		//Cuenta PreExistente
		try {
			cf.setIban(CFPE);
			gestionCuenta.crearCuentaFintech(cf);
		}catch(CuentaExistenteException e) {
			// OK
		}catch (EburyAppException e) {
			fail("No debería lanzar esta excepción: " + e.getMessage() + "-" + e.getClass());
		}catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Iban null
		try {
			cf.setIBAN(null);
			gestionCuenta.crearCuentaFintech(cf);
		}catch (DatosIncorrectosException e){
			//OK
		}catch(Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Cliente null
		try {
			cf.setIBAN("cfIdeal");
			cf.setCliente(null);
			gestionCuenta.crearCuentaFintech(cf);
		}catch (DatosIncorrectosException e){
			//OK
		}catch(Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Cliente PreExistente
		try {
			cliente.setID(Long.valueOf(0));
			cf.setCliente(cliente);
			gestionCuenta.crearCuentaFintech(cf);
		}catch (DatosIncorrectosException e){
			//OK
		}catch(Exception e) {
			fail("No debería lanzar esta excepción: " + e.getMessage() + "-" + e.getClass());
		}
		
		CuentaFintech cfIdealPooled = new Pooled();
		CuentaFintech cfIdealSegregada = new Segregada();
		try {
			cfIdealPooled.setCliente(cliente);
			cfIdealPooled.setEstado("ACTIVO");
			cfIdealPooled.setFechaApertura(date.parse("2019-09-19"));
			cfIdealPooled.setIBAN("cfIdealPooled-2");
			cfIdealPooled.setSwift("Swift");
			
			cfIdealSegregada.setCliente(cliente);
			cfIdealSegregada.setEstado("ACTIVO");
			cfIdealSegregada.setFechaApertura(date.parse("2019-09-19"));
			cfIdealSegregada.setIBAN("cfIdealSegregada-2");
			cfIdealSegregada.setSwift("Swift");
		}catch(ParseException e) {
			throw new RuntimeException(e);
		}
		
		// Cuenta Pooled Ideal
		try {
			gestionCuenta.crearCuentaFintech(cfIdealPooled);
		}catch(Exception e) {
			fail("No debería lanzar n excepción: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Cuenta Segregada Ideal
		try {
			gestionCuenta.crearCuentaFintech(cfIdealSegregada);
		}catch(Exception e) {
			fail("No debería lanzar ninguna excepción: " + e.getMessage() + "-" + e.getClass());
		}
	}
	
	@Test
	public void testCerrarCuentaFintech() {

		final String CFCI = "cfCuentaInactiva";
		final String CFIP = "cfIdealPooled";
		final String CFIS = "cfIdealSegregada";
		
		// Cuenta no existente
		try {
			gestionCuenta.cerrarCuentaFintech("IBANRandomQueNoExisteEnLaBD");
		} catch(CuentaNoExistenteException e) {
			//OK
		} catch(Exception e) {
			fail("No debería lanzar esta excepción" + e.getMessage() + "-" + e.getClass());
		}
		
		// Cuenta Inactiva
		try {
			gestionCuenta.cerrarCuentaFintech(CFCI);
		} catch(CuentaNoExistenteException e) {
			//OK
		} catch(Exception e) {
			fail("No debería lanzar esta excepción" + e.getMessage() + "-" + e.getClass());
		}
		
		// Cuenta Pooled Ideal
		try {
			gestionCuenta.cerrarCuentaFintech(CFIP);
		} catch(Exception e) {
			fail("No debería lanzar ninguna excepción" + e.getMessage() + "-" + e.getClass());
		}
		
		// Cuenta Segregada Ideal
		try {
			gestionCuenta.cerrarCuentaFintech(CFIS);
		} catch(Exception e) {
			fail("No debería lanzar ninguna excepción" + e.getMessage() + "-" + e.getClass());
		}
	}
	
}
	

