package es.uma.softcoders.eburyApp.test;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.CuentaReferencia;
import es.uma.softcoders.eburyApp.Divisa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.Pooled;
import es.uma.softcoders.eburyApp.Segregada;
import es.uma.softcoders.eburyApp.ejb.GestionCuenta;
import es.uma.softcoders.eburyApp.exceptions.ClienteInexistenteException;
import es.uma.softcoders.eburyApp.exceptions.CuentaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoExistenteException;
import es.uma.softcoders.eburyApp.exceptions.DatosIncorrectosException;

public class PruebaCuenta {
	private static final String CUENTA_EJB = "java:global/classes/CuentaEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "eburyAppTest";
	
	private GestionCuenta gestionCuenta;	
	
	@Before
	public void setup() throws NamingException, ParseException  {
		gestionCuenta = (GestionCuenta) SuiteTest.ctx.lookup(CUENTA_EJB);
		BaseDatosCT.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
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
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		
		Segregada cf = new Segregada();
		Individual cliente = new Individual();
		try {
			cliente.setId(0000L);
			cliente.setIdentificacion("0022");
			cliente.setTipoCliente("INDIVIDUAL");
			cliente.setEstado("ACTIVO");
			cliente.setFechaAlta(date.parse("2022-05-12"));
			cliente.setDireccion("Calle prueba, 32");
			cliente.setCiudad("Malaga");
			cliente.setCodigoPostal("29010");
			cliente.setPais("España");
			cliente.setNombre("Cliente");
			cliente.setApellido("Prueba");
			cliente.setFechaNacimiento(date.parse("2002-30-04"));
			cliente.setId(0000L);
			cf.setCliente(cliente);
			cf.setEstado("ACTIVO");
			cf.setFechaApertura(date.parse("2019-06-28"));
			cf.setIban("cpSegregada");	//Pre-existente
			cf.setSwift("SwiftNuevo");
		}catch (ParseException e) {
			throw new RuntimeException(e);
		}
		
		// Cuenta PreExistente
		try {
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
			cf.setIban("cfIdeal-22");
			cf.setCliente(null);
			gestionCuenta.crearCuentaFintech(cf);
		}catch (DatosIncorrectosException e){
			//OK
		}catch(Exception e) {
			fail("No debería lanzar esta excepción-3: " + e.getMessage() + "-" + e.getClass());
		}
		
		// Cliente Inexistente
		try {
			cliente.setId(0022L);
			cf.setCliente(cliente);
			gestionCuenta.crearCuentaFintech(cf);
		}catch (ClienteInexistenteException e){
			//OK
		}catch(Exception e) {
			fail("No debería lanzar esta excepción-4: " + e.getMessage() + "-" + e.getClass());
		}
		
		Pooled cfIdealPooled = new Pooled();
		Segregada cfIdealSegregada = new Segregada();
		Divisa dEuro = new Divisa("EUR", "euros", '€', 1.00);
		CuentaReferencia cr = new CuentaReferencia();
		cliente.setId(0001L);
		try {
			cr.setIban("cfPruebaE");
			cr.setNombreBanco("Santander");
			cr.setSaldo(10000.00);
			cr.setFechaApertura(date.parse("2010-02-22"));
			cr.setEstado("ACTIVO");
			cr.setSwift("Swift");
			cr.setSucursal("Madrid");
			cr.setDivisa(dEuro);
			Map<CuentaReferencia,Double> depositado = new HashMap<>();
			depositado.put(cr, 5000.00);
			cfIdealPooled.setDepositadaEn(depositado);
			cfIdealPooled.setCliente(cliente);
			cfIdealPooled.setEstado("ACTIVO");
			cfIdealPooled.setFechaApertura(date.parse("2019-09-19"));
			cfIdealPooled.setIban("cfIdealPooled-2-22");
			cfIdealPooled.setSwift("SwiftIP");
			
			cfIdealSegregada.setCliente(cliente);
			cfIdealSegregada.setEstado("ACTIVO");
			cfIdealSegregada.setFechaApertura(date.parse("2019-09-19"));
			cfIdealSegregada.setIban("cfIdealSegregada-2-22");
			cfIdealSegregada.setSwift("SwiftIS");
			cfIdealSegregada.setCuentaRef(cr);
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
		
		// Cuenta no existente
		try {
			gestionCuenta.cerrarCuentaFintech("IBANRandomQueNoExisteEnLaBD-22");
		} catch(CuentaNoExistenteException e) {
			//OK
		} catch(Exception e) {
			fail("No debería lanzar esta excepción-1:" + e.getMessage() + "-" + e.getClass());
		}
		
		// Cuenta Pooled Ideal
		try {
			gestionCuenta.cerrarCuentaFintech("cpPooled");
		} catch(Exception e) {
			fail("No debería lanzar ninguna excepción-3:" + e.getMessage() + "-" + e.getClass());
		}
		
		// Cuenta Segregada Ideal
		try {
			gestionCuenta.cerrarCuentaFintech("cpSegregada");
		} catch(Exception e) {
			fail("No debería lanzar ninguna excepción-4:" + e.getMessage() + "-" + e.getClass());
		}
	}
	
}
	

