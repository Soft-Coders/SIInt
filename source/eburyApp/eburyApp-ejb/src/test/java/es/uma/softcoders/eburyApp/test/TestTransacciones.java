package es.uma.softcoders.eburyApp.test;

import java.util.logging.Logger;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.softcoders.eburyApp.ejb.GestionTransaccion;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;

public class TestTransacciones {
	private static final Logger LOG = Logger.getLogger(TestTransacciones.class.getCanonicalName());

	private static final String TRANSACCION_EJB = "java:global/classes/TransaccionEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "eburyAppTest";
	
	private GestionTransaccion gestionTransaccion;
	
	
	@Before
	public void setup() throws NamingException  {
		gestionTransaccion = (GestionTransaccion) SuiteTest.ctx.lookup(TRANSACCION_EJB);
		BaseDatosCT.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	@Test
	public void testCambioDivisa() {
		try {
			
		}catch(EburyAppException e) {
			
		}
	}
}
	

