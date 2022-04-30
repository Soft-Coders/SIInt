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
	
	/**	Este test comprueba el metodo altaCliente() en los casos:
	*	    	<ul><li>Dar de alta a un cliente, caso ideal</li>
	*			<li>Dar de alta a un cliente con identificacion nula (ERROR)</li>
	*			<li>Dar de alta a un cliente con tipo nulo (ERROR)</li>
	*			<li>Dar de alta a un cliente con estado nulo (ERROR)</li>
	*			<li>Dar de alta a un cliente con fecha de alta nula (ERROR)</li>
	*			<li>Dar de alta a un cliente con direccion nula (ERROR)</li>
	*			<li>Dar de alta a un cliente con ciudad nula (ERROR)</li>
	*			<li>Dar de alta a un cliente con codigo postal nulo (ERROR)</li>
	*			<li>Dar de alta a un cliente con pais nulo (ERROR)</li></ul>
	*/
	
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
	
	/**
	 * Este método se encarga de comprobar que la <b>modificación</b> de un Cliente se realiza de la forma esperada.
	 * Teniendo en cuenta situaciones excepcionales que deben de ser abordadas de una forma oportuna.
	 * En concreto este test tiene en cuenta los siguientes casos:
	 * <ul>
	 * <li>Caso ideal -> No debe lanzar excepciones</li>
	 * <li>Fecha de alta null -> Debería lanzar excepción</li>
	 * <li>Id de cliente incorrecto -> Debería lanzar excepción</li>
	 * <li>Cliente null -> Debería lanzar excepción</li>
	 * </ul>
	 * @author Pablo Huertas
	 */
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
	
	/**
	* Este método comprueba que la comprobación de las condiciones necesarias para Cliente y Administrativo se realiza de manera satisfactoria.
	* Los casos que se tienen en cuenta en este test son:
	* <ul>
	* <li>Caso ideal de Cliente -> No debería lanzar excepción</li>
	* <li>Caso ideal de Adminisitrativo -> No debería lanzar excepción</li>
	* <li>Se comprueba autorizado de un Cliente -> Debería lanzar excepción</li>
	* <li>Se comprueba Cliente con ID erróneo -> Debería lanzar excepción</li>
	* </ul>
	*/
	@Test
	@Requisitos({"RF10"})
    public void comprobarCliente() {
		
		try {
    		gestionCliente.comprobarCliente(Long.valueOf(34));
    	}catch(Exception e) {
    		fail("No debería lanzar ninguna excepción: " + e.getMessage() + "-" + e.getClass());
    	}
		
		try {
			gestionCliente.comprobarAutorizado(Long.valueOf(37));
		}catch(Exception e) {
			fail("No debería lanzar ninguna excepción: " + e.getMessage() + "-" + e.getClass());
		}
		
		try {
			gestionCliente.comprobarAutorizado(Long.valueOf(34));
			fail("Debería de lanzar excepción");
		}catch(Exception e) {
			//Ok
		}
		
		try {
			gestionCliente.comprobarCliente(Long.valueOf(38));
			fail("Debería de lanzar excepción");
		}catch(Exception e) {
			//Ok
		}
		
		
    }
	
	/** 
	* Este test comprueba que se dé de baja un cliente de la base de datos. 
	* Los datos del Cliente no se eliminan por completo, sino que se cambia su estado para 
	* establecerlo como "INACTIVO"
	* El test sirve para comprobar el Requisito RF4: Baja de un cliente
	* @author Pablo Huertas
	*/
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
