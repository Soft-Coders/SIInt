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
import es.uma.softcoders.eburyApp.CuentaReferencia;
import es.uma.softcoders.eburyApp.Divisa;
import es.uma.softcoders.eburyApp.ejb.GestionTransaccion;
import es.uma.softcoders.eburyApp.ejb.TransaccionEJB;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoExistenteException;
import es.uma.softcoders.eburyApp.exceptions.DivisaInexistenteException;

public class TestTransacciones {
	private static final String TRANSACCION_EJB = "java:global/classes/TransaccionEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "eburyAppTest";
	
	private GestionTransaccion gestionTransaccion;
	
	
	@Before
	public void setup() throws NamingException, ParseException  {
		gestionTransaccion = (GestionTransaccion) new TransaccionEJB();
		BaseDatosCT.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	@Test
	@Requisitos({"RF17, RF18"})
	public void testCambioDivisa() {

		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		
		CuentaReferencia cfPrueba = new CuentaReferencia();
		Cliente cliente = new Cliente("0000", "tipo", "ACTIVO", new Date(), "Calle calle, 1", "ciudad", "29620", "pais");
		cliente.setID(Long.valueOf(0122));
		
		try {
			
			//Cuenta referencia
			cfPrueba.setIban("cfPrueba");
			cfPrueba.setNombreBanco("Santander");
			cfPrueba.setSaldo(Long.valueOf(10000));
			cfPrueba.setFechaApertura(date.parse("2010-02-22"));
			cfPrueba.setEstado("ACTIVO");
			cfPrueba.setSwift("Swift");
			cfPrueba.setSucursal("Madrid");
			
			//Cuenta pooled
			//Pooled pPrueba = new Pooled();
			//pPrueba.setCliente(cliente);
			//pPrueba.setEstado("ACTIVO");
			//pPrueba.setFechaApertura(date.parse("2019-09-19"));
			//pPrueba.setIban("cfIdealPooled-2-22");
			//pPrueba.setSwift("Swift");
			
			
			//Divisas
			Divisa dDolar = new Divisa("GBP", "libras", '£', (long)0.94);
			Divisa dEuro = new Divisa("EUR", "euros", '€', (long)1);
			
			//Prueba de transaccion
			gestionTransaccion.cambioDivisa(cfPrueba.getIban(), dDolar.getAbreviatura(), dEuro.getAbreviatura(), (long)100);
			
		}catch(DivisaInexistenteException|CuentaNoExistenteException e) {
			fail("No debería de lanzar esta excepcion");
		}catch(Exception e) {
			fail("No debería lanzar ninguna excepción");
		}
		
		try {
			
			//Cuenta referencia
			cfPrueba.setIban("cfPrueba");
			cfPrueba.setNombreBanco("Santander");
			cfPrueba.setSaldo(Long.valueOf(10000));
			cfPrueba.setFechaApertura(date.parse("2010-02-22"));
			cfPrueba.setEstado("ACTIVO");
			cfPrueba.setSwift("Swift");
			cfPrueba.setSucursal("Madrid");
			
			//Divisas
			Divisa dDolar = new Divisa("GBP", "libras", '£', (long)0.94);
			Divisa dRupia = new Divisa("INR", "rupias", '₹', (long)0.012);
			
			//Prueba de Transaccion
			gestionTransaccion.cambioDivisa(cfPrueba.getIban(), dDolar.getAbreviatura(), dRupia.getAbreviatura(), (long)100);
			
		}catch(DivisaInexistenteException|CuentaNoExistenteException e) {
			fail("No debería de lanzar esta excepcion");
		}catch(Exception e) {
			fail("No debería lanzar esta excepción");
		}
		
		try {
			
			//Cuenta referencia
			cfPrueba.setIban("cfPrueba");
			cfPrueba.setNombreBanco("Santander");
			cfPrueba.setSaldo(Long.valueOf(10000));
			cfPrueba.setFechaApertura(date.parse("2010-02-22"));
			cfPrueba.setEstado("ACTIVO");
			cfPrueba.setSwift("Swift");
			cfPrueba.setSucursal("Madrid");
			
			//Divisa
			Divisa dRupia = new Divisa("INR", "rupias", '₹', (long)0.012);
			
			//Prueba de Transaccion
			gestionTransaccion.cambioDivisa(cfPrueba.getIban(), null, dRupia.getAbreviatura(), (long)100);
			
			fail("Debe de lanzar la excepcion DivisaInexsitenteException");
		}catch(DivisaInexistenteException e) {
			// OK 
		}catch(CuentaNoExistenteException e){
			fail("No");
		}catch(Exception e) {
			// OK
		}
		
		try {
			
			//Cuenta referencia
			cfPrueba.setIban("cfPrueba");
			cfPrueba.setNombreBanco("Santander");
			cfPrueba.setSaldo(Long.valueOf(10000));
			cfPrueba.setFechaApertura(date.parse("2010-02-22"));
			cfPrueba.setEstado("ACTIVO");
			cfPrueba.setSwift("Swift");
			cfPrueba.setSucursal("Madrid");
			
			//Divisa
			Divisa dRupia = new Divisa("INR", "rupias", '₹', (long)0.012);
			
			//Prueba de Transaccion
			gestionTransaccion.cambioDivisa(cfPrueba.getIban(), dRupia.getAbreviatura(), null, (long)100);
			
			fail("Debe de lanzar la excepcion DivisaInexsistenteException");
		}catch(DivisaInexistenteException e) {
			// OK 
		}catch(CuentaNoExistenteException e){
			fail("Debe de lanzar la excepcion DivisaInexsistenteException");
		}catch(Exception e) {
			// OK
		}
		
		try {
			//Divisas
			Divisa dDolar = new Divisa("GBP", "libras", '£', (long)0.94);
			Divisa dEuro = new Divisa("EUR", "euros", '€', (long)1);
			
			//Prueba de transaccion
			gestionTransaccion.cambioDivisa(null, dDolar.getAbreviatura(), dEuro.getAbreviatura(), (long)100);
			fail("Debería de lanzar excepcion");
		}catch(Exception e) {
			//OK
		}
		
		
		
	}
	
}	

