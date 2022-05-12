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
import es.uma.softcoders.eburyApp.exceptions.CuentaNoExistenteException;
import es.uma.softcoders.eburyApp.exceptions.DivisaInexistenteException;

public class PruebaTransacciones {
	private static final String TRANSACCION_EJB = "java:global/classes/TransaccionEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "eburyAppTest";
	
	private GestionTransaccion gestionTransaccion;
	
	
	@Before
	public void setup() throws NamingException, ParseException  {
		gestionTransaccion = (GestionTransaccion) SuiteTest.ctx.lookup(TRANSACCION_EJB);
		BaseDatosCT.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	/**
	* Este test comprueba que se realicen correctamente los cambios de divisas. 
	* Un cambio de divisas es una transacción que relaciona dos CuentaReferencia
	* que poseen las divisas indicadas y están asociadas a la misma Pooled. 
	* El test sirve para comprobar los Requisitos:
	* <ul>
	*	 <li>RF17: Cambio de divisas realizadas por el cliente/autorizado</li>
	* 	 <li>RF18: Cambio de divisas realizadas por el administrativo</li>
	* </ul>
	* @author Pablo Huertas
	*/
	@Test
	@Requisitos({"RF17, RF18"})
	public void testCambioDivisa() {
		
		//Prueba de operación normal
		try {

			//Prueba de transaccion
			gestionTransaccion.cambioDivisa("cpPooled", "DOL", "EUR", 100L);
			
		}catch(DivisaInexistenteException|CuentaNoExistenteException e) {
			fail(e.getMessage());
		}catch(Exception e) {
			fail(e.getMessage());
		}
		
		//Prueba de operación sin la primera divisa introducida
		try {
			
			//Prueba de Transaccion
			gestionTransaccion.cambioDivisa("cpPooled", null, "EUR", 100L);
			
			fail("Debe de lanzar la excepcion DivisaInexsitenteException");
		}catch(DivisaInexistenteException e) {
			fail(e.getMessage());
		}catch(CuentaNoExistenteException e){
			fail(e.getMessage());
		}catch(Exception e) {
			//OK
		}
		
		//Prueba sin la segunda divisa introducida
		try {

			//Prueba de Transaccion
			gestionTransaccion.cambioDivisa("cpPooled", "EUR", null, 100L);
			
			fail("Debe de lanzar la excepcion DivisaInexsistenteException");
		}catch(DivisaInexistenteException e) {
			fail(e.getMessage());
		}catch(CuentaNoExistenteException e){
			fail("Debe de lanzar la excepcion DivisaInexsistenteException");
		}catch(Exception e) {
			//OK
		}
		
		//Prueba sin la cuenta Pooled introducida
		try {
			//Divisas
			Divisa dDolar = new Divisa("GBP", "libras", '£', (long)0.94);
			Divisa dEuro = new Divisa("EUR", "euros", '€', (long)1);
			
			//Prueba de transaccion
			gestionTransaccion.cambioDivisa(null, "DOL", "EUR", 100L);
			fail("Debería de lanzar excepcion");
		}catch(CuentaNoExistenteException e) {
			fail(e.getMessage());
		}catch(Exception e) {
			//OK
		}
		
		//Prueba con una cuenta Pooled no registrada
		try {

			//Prueba de Transaccion
			gestionTransaccion.cambioDivisa("cuentaNoExistente", "EUR", "DOL", 100L);
			
			fail("Debe de lanzar la excepcion DivisaInexsistenteException");
		}catch(DivisaInexistenteException e) {
			fail(e.getMessage());
		}catch(CuentaNoExistenteException e){
			//OK
		}catch(Exception e) {
			fail(e.getMessage());
		}
		
		//Prueba con una divisa de entrada no registrada
		try {

			//Prueba de Transaccion
			gestionTransaccion.cambioDivisa("cpPooled", "LIB", "DOL", 100L);
			
			fail("Debe de lanzar la excepcion DivisaInexsistenteException");
		}catch(DivisaInexistenteException e) {
			//OK
		}catch(CuentaNoExistenteException e){
			fail(e.getMessage());
		}catch(Exception e) {
			fail(e.getMessage());
		}
		
		
		//Prueba con una divisa destino no registrada
		try {

			//Prueba de Transaccion
			gestionTransaccion.cambioDivisa("cpPooled", "EUR", "LIB", 100L);
			
			fail("Debe de lanzar la excepcion DivisaInexsistenteException");
		}catch(DivisaInexistenteException e) {
			//OK
		}catch(CuentaNoExistenteException e){
			fail(e.getMessage());
		}catch(Exception e) {
			fail(e.getMessage());
		}
		
		
	}
	
}	

