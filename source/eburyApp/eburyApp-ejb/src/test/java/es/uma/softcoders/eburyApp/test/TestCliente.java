package es.uma.softcoders.eburyApp.test;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Date;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.ejb.GestionTransaccion;
import es.uma.softcoders.eburyApp.ejb.ClienteEJB;
import es.uma.softcoders.eburyApp.ejb.GestionCliente;
import es.uma.softcoders.eburyApp.ejb.TransaccionEJB;

public class TestCliente {
	private static final String TRANSACCION_EJB = "java:global/classes/TransaccionEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "eburyAppTest";
	
	private GestionCliente gestionCliente;
	
	
	@Before
	public void setup() throws NamingException, ParseException  {
		gestionCliente = (GestionCliente) new ClienteEJB();
		BaseDatosCT.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	@Test
	@Requisitos({"RF2"})
	public void testAltaCliente() {
		try {
			Cliente c = new Cliente("0000", "tipo", "ACTIVO", new Date(), "Calle calle, 1", "ciudad", "29620", "pais");
			gestionCliente.altaCliente(c);
		}catch(Exception e) {
			fail("No debería lanzar ninguna excepción: " + e.getMessage() + "-" + e.getClass());
		}
		
		try {
			Cliente c = new Cliente(null, "tipo", "ACTIVO", new Date(), "Calle calle, 1", "ciudad", "29620", "pais");
			gestionCliente.altaCliente(c);
			fail("Debería lanzar alguna excepción");
		}catch(Exception e) {
			//OK
		}
		
		try {
			Cliente c = new Cliente("0000", null, "ACTIVO", new Date(), "Calle calle, 1", "ciudad", "29620", "pais");
			gestionCliente.altaCliente(c);
			fail("Debería lanzar alguna excepción");
		}catch(Exception e) {
			//OK
		}
		
		try {
			Cliente c = new Cliente("0000", "tipo", null, new Date(), "Calle calle, 1", "ciudad", "29620", "pais");
			gestionCliente.altaCliente(c);
			fail("Debería lanzar alguna excepción");
		}catch(Exception e) {
			//OK
		}
		
		try {
			Cliente c = new Cliente("0000", "tipo", "ACTIVO", null, "Calle calle, 1", "ciudad", "29620", "pais");
			gestionCliente.altaCliente(c);
			fail("Debería lanzar alguna excepción");
		}catch(Exception e) {
			//OK
		}
		
		try {
			Cliente c = new Cliente("0000", "tipo", "ACTIVO", new Date(), null, "ciudad", "29620", "pais");
			gestionCliente.altaCliente(c);
			fail("Debería lanzar alguna excepción");
		}catch(Exception e) {
			//OK
		}
		
		try {
			Cliente c = new Cliente("0000", "tipo", "ACTIVO", new Date(), "Calle calle, 1", null, "29620", "pais");
			gestionCliente.altaCliente(c);
			fail("Debería lanzar alguna excepción");
		}catch(Exception e) {
			//OK
		}
		
		try {
			Cliente c = new Cliente("0000", "tipo", "ACTIVO", new Date(), "Calle calle, 1", "ciudad", null, "pais");
			gestionCliente.altaCliente(c);
			fail("Debería lanzar alguna excepción");
		}catch(Exception e) {
			//OK
		}
		
		try {
			Cliente c = new Cliente("0000", "tipo", "ACTIVO", new Date(), "Calle calle, 1", "ciudad", "29620", null);
			gestionCliente.altaCliente(c);
			fail("Debería lanzar alguna excepción");
		}catch(Exception e) {
			//OK
		}


	}
	
	@Test
	@Requisitos({"RF3"})
    public void modificarCliente() {
		try {
			Cliente aux = new Cliente("0001", "tipo", "ACTIVO", new Date(), "Calle calle, 1", "malaga", "29010", "pais");
			gestionCliente.modificarCliente(aux, Long.getLong("1111"));
		}catch(Exception e) {
			fail("No debería lanzar ninguna excepción: " + e.getMessage() + "-" + e.getClass());
		}
		
		try {
			Cliente aux = new Cliente("0001", "tipo", "ACTIVO", new Date(), "Calle calle, 1", "malaga", "29010", "España");
			gestionCliente.modificarCliente(aux, Long.getLong("1111"));
		}catch(Exception e) {
			fail("No debería lanzar ninguna excepción: " + e.getMessage() + "-" + e.getClass());
		}
		
		try {
			Cliente aux = new Cliente("0001", "tipo", "ACTIVO", null, "Calle Pepa Flores, 34", "Málaga", "29010", "pais");
			gestionCliente.modificarCliente(aux, Long.getLong("1111"));
		}catch(Exception e) {
			fail("No debería lanzar ninguna excepción: " + e.getMessage() + "-" + e.getClass());
		}
	
    	try {
    		Cliente aux = new Cliente("0001", "tipo", "ACTIVO", null, "Calle Pepa Flores, 34", "Málaga", "29010", "pais");
    		gestionCliente.modificarCliente(aux, Long.getLong("noExiste"));
    		fail("Debería de lanzar una excepcion");
    	}catch(Exception e) {
    		//OK
    	}
    	
    	try {
    		gestionCliente.modificarCliente(null, Long.getLong("1111"));
    		fail("Debería de lanzar una excepcion");
    	}catch(Exception e) {
    		//OK
    	}
		
    }
	
	@Test
	@Requisitos({"RF10"})
    public void comprobarCliente() {
		
		//Debería de pasarle por referencia el identificador del usuario, el id se crea automáticamente, por lo que no puedo acceder a él simplemente sabíendomelo
		//Una vez cambiado el test sería así
		
		try {
    		gestionCliente.comprobarCliente("pepesado34");
    	}catch(Exception e) {
    		fail("No debería lanzar ninguna excepción: " + e.getMessage() + "-" + e.getClass());
    	}
		
		try {
			gestionCliente.comprobarAutorizado("admin37");
		}catch(Exception e) {
			fail("No debería lanzar ninguna excepción: " + e.getMessage() + "-" + e.getClass());
		}
		
		try {
			gestionCliente.comprobarAutorizado("pepesado34");
			fail("Debería de lanzar excepción");
		}catch(Exception e) {
			//Ok
		}
		
		try {
			gestionCliente.comprobarCliente("admin");
		}catch(Exception e) {
			fail("Debería de lanzar excepción");
		}
		
		
    }
	
	@Test
	@Requisitos({"RF4"})
    public void bajaCliente() {
    	try {
    		gestionCliente.bajaCliente(Long.getLong("2222"));
    	}catch(Exception e) {
    		fail("No debería lanzar ninguna excepción: " + e.getMessage() + "-" + e.getClass());
    	}
    	
    	try {
    		gestionCliente.bajaCliente(Long.getLong("3333"));
    	}catch(Exception e) {
    		fail("No debería lanzar ninguna excepción: " + e.getMessage() + "-" + e.getClass());
    	}
    	
    	try {
    		gestionCliente.bajaCliente(Long.getLong("noExiste"));
    		fail("Debería de lanzar una excepcion");
    	}catch(Exception e) {
    		//OK
    	}
    	
    	
    }
}
