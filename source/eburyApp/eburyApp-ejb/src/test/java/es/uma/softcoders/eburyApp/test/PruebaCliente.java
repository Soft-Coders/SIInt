package es.uma.softcoders.eburyApp.test;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.ejb.GestionCliente;
import es.uma.softcoders.eburyApp.exceptions.ClienteNuloException;

public class PruebaCliente {
	private static final String CLIENTE_EJB = "java:global/classes/ClienteEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "eburyAppTest";
	
	private GestionCliente gestionCliente;
	
	
	@Before
	public void setup() throws NamingException, ParseException  {
		gestionCliente = (GestionCliente) SuiteTest.ctx.lookup(CLIENTE_EJB);
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
		//Insertar individual
		try {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			Individual c = new Individual();
			c.setIdentificacion("0001");
			c.setTipoCliente("INDIVIDUAL");
			c.setEstado("BAJA");
			c.setFechaAlta(date.parse("2022-05-12"));
			c.setDireccion("Calle prueba, 32");
			c.setCiudad("Malaga");
			c.setCodigoPostal("29010");
			c.setPais("Espa??a");
			c.setNombre("Cliente");
			c.setApellido("Prueba");
			c.setFechaNacimiento(date.parse("2002-30-04"));
			
			//gestionCliente.altaCliente(c, 1111L, "1234");
			
		}catch(Exception e) {
			fail("No deber??a lanzar ninguna excepci??n: " + e.getMessage() + "-" + e.getClass());
		}
		
		//Insertar empresa
		try {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			Empresa c = new Empresa();
			c.setIdentificacion("0001");
			c.setTipoCliente("INDIVIDUAL");
			c.setEstado("BAJA");
			c.setFechaAlta(date.parse("2022-05-12"));
			c.setDireccion("Calle prueba, 32");
			c.setCiudad("Malaga");
			c.setCodigoPostal("29010");
			c.setPais("Espa??a");
			c.setRazonSocial("Razon");
			
			//gestionCliente.altaCliente(c, null, null);
			
		}catch(Exception e) {
			fail("No deber??a lanzar ninguna excepci??n: " + e.getMessage() + "-" + e.getClass());
		}
		
		//Atributos nulos
		try {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			Individual c = new Individual();
			c.setIdentificacion(null);
			c.setTipoCliente("INDIVIDUAL");
			c.setEstado("BAJA");
			c.setFechaAlta(date.parse("2022-05-12"));
			c.setDireccion("Calle prueba, 32");
			c.setCiudad("Malaga");
			c.setCodigoPostal("29010");
			c.setPais("Espa??a");
			c.setNombre("Cliente");
			c.setApellido("Prueba");
			c.setFechaNacimiento(date.parse("2002-30-04"));
			
			//gestionCliente.altaCliente(c, 1111L, "1234");
			fail("Deber??a lanzar ninguna excepci??n");
		}catch(Exception e) {
			//Ok
		}
		
		try {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			Individual c = new Individual();
			c.setIdentificacion("0001");
			c.setTipoCliente("INDIVIDUAL");
			c.setEstado("BAJA");
			c.setFechaAlta(null);
			c.setDireccion("Calle prueba, 32");
			c.setCiudad("Malaga");
			c.setCodigoPostal("29010");
			c.setPais("Espa??a");
			c.setNombre("Cliente");
			c.setApellido("Prueba");
			c.setFechaNacimiento(date.parse("2002-30-04"));
			
			//gestionCliente.altaCliente(c, 1111L, "1234");
			fail("Deber??a lanzar ninguna excepci??n");
		}catch(Exception e) {
			//Ok
		}
		
		try {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			Individual c = new Individual();
			c.setIdentificacion("0001");
			c.setTipoCliente("INDIVIDUAL");
			c.setEstado("BAJA");
			c.setFechaAlta(date.parse("2022-05-12"));
			c.setDireccion("Calle prueba, 32");
			c.setCiudad(null);
			c.setCodigoPostal("29010");
			c.setPais("Espa??a");
			c.setNombre("Cliente");
			c.setApellido("Prueba");
			c.setFechaNacimiento(date.parse("2002-30-04"));
			
			//gestionCliente.altaCliente(c, 1111L, "1234");
			fail("Deber??a lanzar ninguna excepci??n");
		}catch(Exception e) {
			//Ok
		}
		
		try {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			Individual c = new Individual();
			c.setIdentificacion("0001");
			c.setTipoCliente("INDIVIDUAL");
			c.setEstado("BAJA");
			c.setFechaAlta(date.parse("2022-05-12"));
			c.setDireccion("Calle prueba, 32");
			c.setCiudad("Malaga");
			c.setCodigoPostal(null);
			c.setPais("Espa??a");
			c.setNombre("Cliente");
			c.setApellido("Prueba");
			c.setFechaNacimiento(date.parse("2002-30-04"));
			
			//gestionCliente.altaCliente(c, 1111L, "1234");
			fail("Deber??a lanzar ninguna excepci??n");
		}catch(Exception e) {
			//Ok
		}
		try {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			Individual c = new Individual();
			c.setIdentificacion("0001");
			c.setTipoCliente("INDIVIDUAL");
			c.setEstado("BAJA");
			c.setFechaAlta(date.parse("2022-05-12"));
			c.setDireccion("Calle prueba, 32");
			c.setCiudad("Malaga");
			c.setCodigoPostal("29010");
			c.setPais(null);
			c.setNombre("Cliente");
			c.setApellido("Prueba");
			c.setFechaNacimiento(date.parse("2002-30-04"));
			
			//gestionCliente.altaCliente(c, 1111L, "1234");
			fail("Deber??a lanzar ninguna excepci??n");
		}catch(Exception e) {
			//Ok
		}
		
		try {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			Individual c = new Individual();
			c.setIdentificacion("0001");
			c.setTipoCliente("INDIVIDUAL");
			c.setEstado("BAJA");
			c.setFechaAlta(date.parse("2022-05-12"));
			c.setDireccion("Calle prueba, 32");
			c.setCiudad("Malaga");
			c.setCodigoPostal("29010");
			c.setPais("Espa??a");
			c.setNombre(null);
			c.setApellido("Prueba");
			c.setFechaNacimiento(date.parse("2002-30-04"));
			
			//gestionCliente.altaCliente(c, 1111L, "1234");
			fail("Deber??a lanzar ninguna excepci??n");
		}catch(Exception e) {
			//Ok
		}
		
		try {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			Individual c = new Individual();
			c.setIdentificacion("0001");
			c.setTipoCliente("INDIVIDUAL");
			c.setEstado("BAJA");
			c.setFechaAlta(date.parse("2022-05-12"));
			c.setDireccion("Calle prueba, 32");
			c.setCiudad("Malaga");
			c.setCodigoPostal("29010");
			c.setPais("Espa??a");
			c.setNombre("Cliente");
			c.setApellido(null);
			c.setFechaNacimiento(date.parse("2002-30-04"));
			
			//gestionCliente.altaCliente(c, 1111L, "1234");
			fail("Deber??a lanzar ninguna excepci??n");
		}catch(Exception e) {
			//Ok
		}

		
		//Usuario incorrecto
		try {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			Individual c = new Individual();
			c.setIdentificacion("0001");
			c.setTipoCliente("INDIVIDUAL");
			c.setEstado("BAJA");
			c.setFechaAlta(date.parse("2022-05-12"));
			c.setDireccion("Calle prueba, 32");
			c.setCiudad("Malaga");
			c.setCodigoPostal("29010");
			c.setPais("Espa??a");
			c.setNombre("Cliente");
			c.setApellido("Prueba");
			c.setFechaNacimiento(date.parse("2002-30-04"));
			
			//gestionCliente.altaCliente(c, 1112L, "1234");
			fail("Deber??a lanzar ninguna excepci??n");
		}catch(Exception e) {
			//Ok
		}
		
		//Contrase??a Incorrecta
		try {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			Individual c = new Individual();
			c.setIdentificacion("0001");
			c.setTipoCliente("INDIVIDUAL");
			c.setEstado("BAJA");
			c.setFechaAlta(date.parse("2022-05-12"));
			c.setDireccion("Calle prueba, 32");
			c.setCiudad("Malaga");
			c.setCodigoPostal("29010");
			c.setPais("Espa??a");
			c.setNombre("Cliente");
			c.setApellido("Prueba");
			c.setFechaNacimiento(date.parse("2002-30-04"));
			
			//gestionCliente.altaCliente(c, 1111L, "2");
			fail("Deber??a lanzar ninguna excepci??n");
		}catch(Exception e) {
			//Ok
		}
		
		//Raz??n social nula en empresa
		try {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			Empresa c = new Empresa();
			c.setIdentificacion("0001");
			c.setTipoCliente("INDIVIDUAL");
			c.setEstado("BAJA");
			c.setFechaAlta(date.parse("2022-05-12"));
			c.setDireccion("Calle prueba, 32");
			c.setCiudad("Malaga");
			c.setCodigoPostal("29010");
			c.setPais("Espa??a");
			c.setRazonSocial(null);
			
			//gestionCliente.altaCliente(c, 1111L, "1234");
			fail("Deber??a lanzar ninguna excepci??n");
		}catch(Exception e) {
			//Ok
		}

	}
	
	/**
	 * Este m??todo se encarga de comprobar que la <b>modificaci??n</b> de un Cliente se realiza de la forma esperada.
	 * Teniendo en cuenta situaciones excepcionales que deben de ser abordadas de una forma oportuna.
	 * En concreto este test tiene en cuenta los siguientes casos:
	 * <ul>
	 * <li>Caso ideal -> No debe lanzar excepciones</li>
	 * <li>Fecha de alta null -> Deber??a lanzar excepci??n</li>
	 * <li>Id de cliente incorrecto -> Deber??a lanzar excepci??n</li>
	 * <li>Cliente null -> Deber??a lanzar excepci??n</li>
	 * </ul>
	 * @author Pablo Huertas
	 */
	@Test
	@Requisitos({"RF3"})
    public void modificarCliente() {
		try {
			
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			Individual c = new Individual();
			c.setIdentificacion("0001");
			c.setTipoCliente("INDIVIDUAL");
			c.setEstado("BAJA");
			c.setFechaAlta(date.parse("2022-05-12"));
			c.setDireccion("Calle tontonabo, 32");
			c.setCiudad("Malaga");
			c.setCodigoPostal("29010");
			c.setPais("Espa??a");
			c.setNombre("Cliente");
			c.setApellido("Modificado");
			c.setFechaNacimiento(date.parse("2002-04-30"));
			
			gestionCliente.modificarCliente(c, 0001L);
		}catch(Exception e) {
			fail("No deber??a lanzar ninguna excepci??n: " + e.getMessage() + "-" + e.getClass());
		}
		/*
		try {
			Cliente aux = new Cliente("0001", "tipo", "ACTIVO", new Date(), "Calle calle, 1", "malaga", "29010", "Espa??a");
			gestionCliente.modificarCliente(aux, Long.getLong("1111"));
		}catch(Exception e) {
			fail("No deber??a lanzar ninguna excepci??n: " + e.getMessage() + "-" + e.getClass());
		}
		
		try {
			Cliente aux = new Cliente("0001", "tipo", "ACTIVO", null, "Calle Pepa Flores, 34", "M??laga", "29010", "pais");
			gestionCliente.modificarCliente(aux, Long.getLong("1111"));
		}catch(Exception e) {
			fail("No deber??a lanzar ninguna excepci??n: " + e.getMessage() + "-" + e.getClass());
		}
	
    	try {
    		Cliente aux = new Cliente("0001", "tipo", "ACTIVO", null, "Calle Pepa Flores, 34", "M??laga", "29010", "pais");
    		gestionCliente.modificarCliente(aux, Long.getLong("noExiste"));
    		fail("Deber??a de lanzar una excepcion");
    	}catch(Exception e) {
    		//OK
    	}
    	
    	try {
    		gestionCliente.modificarCliente(null, Long.getLong("1111"));
    		fail("Deber??a de lanzar una excepcion");
    	}catch(Exception e) {
    		//OK
    	}
    	*/
		
    }
	
	/**
	* Este m??todo comprueba que la comprobaci??n de las condiciones necesarias para Cliente y Administrativo se realiza de manera satisfactoria.
	* Los casos que se tienen en cuenta en este test son:
	* <ul>
	* <li>Caso ideal de Cliente -> No deber??a lanzar excepci??n</li>
	* <li>Caso ideal de Adminisitrativo -> No deber??a lanzar excepci??n</li>
	* <li>Se comprueba autorizado de un Cliente -> Deber??a lanzar excepci??n</li>
	* <li>Se comprueba Cliente con ID err??neo -> Deber??a lanzar excepci??n</li>
	* </ul>
	*/
	
	@Test
	@Requisitos({"RF10"})
    public void comprobarCliente() {
		
		//Cliente individual normal
		try {
    		gestionCliente.comprobarCliente(Long.valueOf(0001));
    	}catch(Exception e) {
    		fail("No deber??a lanzar ninguna excepci??n: " + e.getMessage() + "-" + e.getClass());
    	}
		
		//Cliente jud??rico
		try {
			gestionCliente.comprobarCliente(1111L);
			fail("Deber??a de lanzar excepci??n");
		}catch(Exception e) {
			//OK
		}
		
    }
	
	
	@Test
	@Requisitos({"RF"})
	public void comprobarAutorizado() {
		try {
			gestionCliente.comprobarAutorizado(1234L);
		}catch(Exception e) {
			fail("No deber??a lanzar ninguna excepci??n: " + e.getMessage() + "-" + e.getClass());
		}
	}
	
	/** 
	* Este test comprueba que se d?? de baja un cliente de la base de datos. 
	* Los datos del Cliente no se eliminan por completo, sino que se cambia su estado para 
	* establecerlo como "INACTIVO"
	* El test sirve para comprobar el Requisito RF4: Baja de un cliente
	* @author Pablo Huertas
	*/

	@Test
	@Requisitos({"RF4"})
    public void bajaCliente() {
    	try {
    		gestionCliente.bajaCliente(0001L);
    	}catch(Exception e) {
    		fail("No deber??a lanzar ninguna excepci??n: " + e.getMessage() + "-" + e.getClass());
    	}
    	
    }
}
