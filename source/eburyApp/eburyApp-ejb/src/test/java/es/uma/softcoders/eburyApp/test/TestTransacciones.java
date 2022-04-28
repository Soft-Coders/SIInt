package es.uma.softcoders.eburyApp.test;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.softcoders.eburyApp.Divisa;
import es.uma.softcoders.eburyApp.Pooled;
import es.uma.softcoders.eburyApp.Transaccion;
import es.uma.softcoders.eburyApp.ejb.GestionTransaccion;
import es.uma.softcoders.eburyApp.ejb.TransaccionEJB;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoExistenteException;
import es.uma.softcoders.eburyApp.exceptions.DivisaInexistenteException;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;

public class TestTransacciones {
	private static final String TRANSACCION_EJB = "java:global/classes/TransaccionEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "eburyAppTest";
	
	private GestionTransaccion gestionTransaccion;
	
	
	@Before
	public void setup() throws NamingException  {
		gestionTransaccion = (GestionTransaccion) new TransaccionEJB();
		BaseDatosCT.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	@Test
	public void testCambioDivisa() {
		
		try {			
			//Prueba de transaccion
			gestionTransaccion.cambioDivisa(pPrueba.getIban(), dDolar.getAbreviatura(), dEuro.getAbreviatura(), (long)100);
			
		}catch(DivisaInexistenteException|CuentaNoExistenteException e) {
			fail("No debería de lanzar esta excepcion");
		}catch(Exception e) {
			fail("No debería lanzar ninguna excepción");
		}
		/*
		try {
			
			//Cuenta pooled
			Pooled pPrueba = new Pooled();
			pPrueba.setIban("Hay que poner IBAN porque si no da NullPointerExceptino que es que no piensas ni un poquito macho");
			
			//Divisas
			Divisa dDolar = new Divisa("GBP", "libras", '£', (long)0.94);
			Divisa dRupia = new Divisa("INR", "rupias", '₹', (long)0.012);
			
			//Prueba de Transaccion
			gestionTransaccion.cambioDivisa(pPrueba.getIban(), dDolar.getAbreviatura(), dRupia.getAbreviatura(), (long)100);
			
		}catch(DivisaInexistenteException|CuentaNoExistenteException e) {
			fail("No debería de lanzar esta excepcion");
		}catch(Exception e) {
			fail("No debería lanzar esta excepción");
		}
		
		try {
			
			//Cuenta pooled
			Pooled pPrueba = new Pooled();
			pPrueba.setIban("Hay que poner IBAN porque si no da NullPointerExceptino que es que no piensas ni un poquito macho");
			
			//Divisa
			Divisa dRupia = new Divisa("INR", "rupias", '₹', (long)0.012);
			
			//Prueba de Transaccion
			gestionTransaccion.cambioDivisa(pPrueba.getIban(), null, dRupia.getAbreviatura(), (long)100);
			
			fail("Debe de lanzar la excepcion DivisaInexsitenteException");
		}catch(DivisaInexistenteException e) {
			// OK 
		}catch(CuentaNoExistenteException e){
			fail("No");
		}catch(Exception e) {
			fail("No debería lanzar esta excepción");
		}
		
		try {
			
			//Cuenta pooled
			Pooled pPrueba = new Pooled();
			pPrueba.setIban("Hay que poner IBAN porque si no da NullPointerExceptino que es que no piensas ni un poquito macho");
			
			//Divisa
			Divisa dRupia = new Divisa("INR", "rupias", '₹', (long)0.012);
			
			//Prueba de Transaccion
			gestionTransaccion.cambioDivisa(pPrueba.getIban(), dRupia.getAbreviatura(), null, (long)100);
			
			fail("Debe de lanzar la excepcion DivisaInexsistenteException");
		}catch(DivisaInexistenteException e) {
			// OK 
		}catch(CuentaNoExistenteException e){
			fail("Debe de lanzar la excepcion DivisaInexsistenteException");
		}catch(Exception e) {
			fail("No debería lanzar esta excepción");
		}
		
		try {
			//Cuenta pooled
			Pooled pPrueba = new Pooled();
			pPrueba.setIban("Hay que poner IBAN porque si no da NullPointerExceptino que es que no piensas ni un poquito macho");

			
			//Divisas
			Divisa dDolar = new Divisa("GBP", "libras", '£', (long)0.94);
			Divisa dEuro = new Divisa("EUR", "euros", '€', (long)1);
			
			//Prueba de transaccion
			gestionTransaccion.cambioDivisa(null, dDolar.getAbreviatura(), dEuro.getAbreviatura(), (long)100);
			
		}catch(DivisaInexistenteException e) {
			fail("No debería de lanzar esta excepcion");
		}catch(CuentaNoExistenteException e) {
			//OK
		}catch(Exception e) {
			fail("No debería lanzar esta excepción");
		}
		
		*/
		
	}
	
}
	

