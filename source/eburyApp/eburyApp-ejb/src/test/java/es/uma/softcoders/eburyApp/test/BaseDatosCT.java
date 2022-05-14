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
		
		//Cliente individual
		Individual c = new Individual();
		c.setID(0000L);
		c.setIdentificacion("0001");
		c.setTipo_cliente("INDIVIDUAL");
		c.setEstado("ACTIVO");
		c.setFecha_Alta(date.parse("2022-05-12"));
		c.setDireccion("Calle prueba, 32");
		c.setCiudad("Malaga");
		c.setCodigoPostal("29010");
		c.setPais("España");
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
		
		
		Pooled cpPooled = new Pooled();
		Usuario uPrueba = new Usuario();
		
		//Usuario
		uPrueba.setId(1111L);
		uPrueba.setUsuario("uPrueba");
		uPrueba.setClave("1234");
		uPrueba.setEsAdministrativo(false);
		uPrueba.setIndividual(c);
		c.setUsuario(uPrueba);
		
		//Cuenta Pooled
		cpPooled.setIban("cpPooled");
		cpPooled.setSwift("Swift");
		cpPooled.setEstado("ACTIVO");
		cpPooled.setFechaApertura(date.parse("2018-05-27"));
		cpPooled.setCliente(c);
		
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
		em.persist(c);
		em.persist(cpPooled);
		em.persist(dDolar);
		em.persist(dEuro);
		em.persist(cfPruebaD);
		em.persist(cfPruebaE);
		
		//Personas Autorizadas
		PersonaAutorizada pa1 = new PersonaAutorizada("ABC123", "Marta", "Maleno", "Calle Patata, 37");
		PersonaAutorizada pa2 = new PersonaAutorizada("ABC456", "Pablo", "Huertas", "Calle Boniato, 8");
		pa1.setId((long)123000);
		pa2.setId((long)456000);
		Usuario u1 = new Usuario("USUARIO1", "clave1", true);
		Usuario u2 = new Usuario("USUARIO2", "clave2", false);
		pa1.setUsuario(u1);
		pa2.setUsuario(u2);
		u1.setPersonaAutorizada(pa1);
		u2.setPersonaAutorizada(pa2);
		em.persist(pa1);
		em.persist(pa2);
		em.persist(u1);
		em.persist(u2);
		
		//Empresas
		Empresa em1 = new Empresa("Empresa1");
		Empresa em2 = new Empresa("Empresa2");
		em1.setID((long)123);
		em2.setID((long)456);
		em1.setIdentificacion("Empresa1");
		em2.setIdentificacion("Empresa2");
		em1.setTipo_cliente("Empresa");
		em2.setTipo_cliente("Empresa");
		em1.setEstado("Activo");
		em1.setFecha_Alta(new Date());
		em1.setDireccion("Calle calle, 0");
		em1.setCiudad("Málaga");
		em1.setCodigoPostal("29000");
		em1.setPais("Esp");
		em2.setEstado("Activo");
		em2.setFecha_Alta(new Date());
		em2.setDireccion("Calle calle, 0");
		em2.setCiudad("Málaga");
		em2.setCodigoPostal("29000");
		em2.setPais("Esp");
		em.persist(em1);
		em.persist(em2);
		
		
		//Cierre Contexto Persistencia
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
