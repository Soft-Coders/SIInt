package es.uma.softcoders.eburyApp.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.CuentaReferencia;
import es.uma.softcoders.eburyApp.Divisa;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Pooled;
import es.uma.softcoders.eburyApp.Segregada;
import es.uma.softcoders.eburyApp.Usuario;

public class BaseDatosCT {
	public static void inicializaBaseDatos(String nombreUnidadPersistencia) throws ParseException {
		//Inicio contexto de persistencia
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		
		CuentaReferencia cfPruebaD = new CuentaReferencia();
		CuentaReferencia cfPruebaE = new CuentaReferencia();
		
		Usuario uPruebaPA = new Usuario();
		//Usuario
		uPruebaPA.setId(2345L);
		uPruebaPA.setUsuario("uPruebaPA");
		uPruebaPA.setClave("1234");
		uPruebaPA.setEsAdministrativo(false);
	
		
		PersonaAutorizada paPrueba = new PersonaAutorizada();
		paPrueba.setId(1234L);
		paPrueba.setIdentificacion("1234");
		paPrueba.setNombre("paPrueba");
		paPrueba.setApellidos("prueba persona");
		paPrueba.setDireccion("calle prueba");
		paPrueba.setUsuario(uPruebaPA);
		paPrueba.setEstado("ACTIVO");
		uPruebaPA.setPersonaAutorizada(paPrueba);
		
		
		//Cliente individual
		Individual c = new Individual();
		c.setID(Long.valueOf(0001));
		c.setIdentificacion("0001");
		c.setTipo_cliente("INDIVIDUAL");
		c.setEstado("ACTIVO");
		c.setFecha_Alta(date.parse("2022-05-12"));
		c.setDireccion("Calle prueba, 32");
		c.setCiudad("Malaga");
		c.setCodigoPostal("29010");
		c.setPais("Alemania");
		c.setNombre("Cliente");
		c.setApellido("Prueba");
		c.setFechaNacimiento(date.parse("2002-30-04"));
		
		//Cliente judírico (empresa)
		Empresa e = new Empresa();
		e.setID(1111L);
		e.setIdentificacion("1111");
		e.setTipo_cliente("EMPRESA");
		e.setEstado("BAJA");
		e.setFecha_Alta(date.parse("2022-05-12"));
		e.setDireccion("Calle prueba, 32");
		e.setCiudad("Malaga");
		e.setCodigoPostal("29010");
		e.setPais("España");
		e.setRazonSocial("Razon");
		
		/*
		Map<Empresa,Character> autorizacionE = new HashMap<>();
		autorizacionE.put(e, 'L');
		paPrueba.setAutorizacion(autorizacionE);	
		
		Map<PersonaAutorizada,Character> autorizacionPA = new HashMap<>();
		autorizacionPA.put(paPrueba, 'L');
		e.setAutorizacion(autorizacionPA);
		
		*/
		
		Pooled cpPooled = new Pooled();
		Usuario uPrueba = new Usuario();
		Usuario uPruebaAdmin = new Usuario();
		
		//Usuario
		uPrueba.setId(1111L);
		uPrueba.setUsuario("uPrueba");
		uPrueba.setClave("1234");
		uPrueba.setEsAdministrativo(false);
		uPrueba.setIndividual(c);
		c.setUsuario(uPrueba);
		
		uPruebaAdmin.setId(2222L);
		uPruebaAdmin.setUsuario("uPruebaAdmin");
		uPruebaAdmin.setClave("1234");
		uPruebaAdmin.setEsAdministrativo(true);
		
		//Cuenta Pooled
		cpPooled.setIban("cpPooled");
		cpPooled.setSwift("Swift");
		cpPooled.setEstado("ACTIVO");
		cpPooled.setFechaApertura(date.parse("2018-05-27"));
		cpPooled.setCliente(c);
		
		//Cuenta Segregada
		Segregada cpSegregada = new Segregada();
		cpSegregada.setIban("cpSegregada");
		cpSegregada.setSwift("SwiftSegregada");
		cpSegregada.setEstado("ACTIVO");
		cpSegregada.setFechaApertura(date.parse("2019-06-28"));
		cpSegregada.setCuentaRef(cfPruebaE);
		cpSegregada.setCliente(c);
		
		//Cuenta Inactiva
		Segregada cpInactiva = new Segregada();
		cpInactiva.setIban("cpInactiva");
		cpInactiva.setSwift("SwiftInactiva");
		cpInactiva.setEstado("INACTIVO");
		cpInactiva.setFechaApertura(date.parse("2019-06-29"));
		cpInactiva.setFechaCierre(date.parse("2019-06-30"));
		cpInactiva.setCuentaRef(cfPruebaD);
		cpInactiva.setCliente(c);
		
		//Divisas
		Divisa dDolar = new Divisa("DOL", "dolares", '$', 2L);
		Divisa dEuro = new Divisa("EUR", "euros", '€', 3L);
		
		//Cuentas referencias
		cfPruebaD.setIban("cfPruebaD");
		cfPruebaD.setNombreBanco("Santander");
		cfPruebaD.setSaldo(Long.valueOf(10000));
		cfPruebaD.setFechaApertura(date.parse("2010-02-22"));
		cfPruebaD.setEstado("ACTIVO");
		cfPruebaD.setSwift("Swift");
		cfPruebaD.setSucursal("Madrid");
		cfPruebaD.setDivisa(dDolar);
		
		cfPruebaE.setIban("cfPruebaE");
		cfPruebaE.setNombreBanco("Santander");
		cfPruebaE.setSaldo(Long.valueOf(10000));
		cfPruebaE.setFechaApertura(date.parse("2010-02-22"));
		cfPruebaE.setEstado("ACTIVO");
		cfPruebaE.setSwift("Swift");
		cfPruebaE.setSucursal("Madrid");
		cfPruebaE.setDivisa(dEuro);
		
		//Relación cuenta referencias - pooled
		Map<CuentaReferencia,Long> depositado = new HashMap<>();
		depositado.put(cfPruebaD, 5000L);
		depositado.put(cfPruebaE, 5000L);
		
		cpPooled.setDepositadaEn(depositado);
		
		//Introducción en la base de datos
		em.persist(uPrueba);
		em.persist(uPruebaAdmin);
		em.persist(c);
		em.persist(cpPooled);
		em.persist(cpSegregada);
		em.persist(cpInactiva);
		em.persist(dDolar);
		em.persist(dEuro);
		em.persist(cfPruebaD);
		em.persist(cfPruebaE);
		em.persist(paPrueba);
		em.persist(uPruebaPA);
		
		//Cierre Contexto Persistencia
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
