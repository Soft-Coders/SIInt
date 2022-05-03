package es.uma.softcoders.eburyApp.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.CuentaReferencia;
import es.uma.softcoders.eburyApp.Divisa;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Segregada;
import es.uma.softcoders.eburyApp.Usuario;

public class BaseDatosInformes {
	private static EntityManagerFactory emf;
	private static Long c1ID;
	private static String cS1IBAN;
	public static void inicializaBaseDatos(String nombreUnidadPersistencia) {
		emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		//Individual
		Date pDay = new Date(117,6,23);
		Individual pInd = new Individual("Pep", "Doe");
		//CUENTA
		Divisa pDiv = new Divisa("GBP", "libras", '£', (long)0.94);
		CuentaReferencia pRef = new CuentaReferencia("CaixaBank", (long)40000, pDiv);
		Segregada pSeg = new Segregada("30",pRef);
		List<CuentaFintech> pCuentas = new ArrayList<CuentaFintech>();

		Usuario u1 = new Usuario("USUARIO1-55","CLAVE1-55",false);

		u1.setIndividual(pInd);

		pSeg.setIban("NL66XYZW1291965209");
		pSeg.setEstado("ACTIVA");
		pSeg.setFechaApertura(pDay);
		pSeg.setCliente(pInd);
		pRef.setIban("NL66XYZW1291965241");

		em.persist(pInd);
		em.persist(pDiv);
		em.persist(pRef);
		em.persist(pSeg);

		// Setters
		pRef.setSegregada(pSeg);
		pInd.setIdentificacion("Ide");
		pInd.setTipo_cliente("INDIVIDUAL");
		pInd.setEstado("ACTIVO");
		pInd.setFecha_Alta(pDay);
		pInd.setDireccion("54");
		pInd.setCiudad("New York");
		pInd.setCodigoPostal("7207KE");
		pInd.setPais("NL");
		pCuentas.add(pSeg);
		pInd.setCuentas(pCuentas);
		pInd.setID(Long.valueOf(1000002));
		pInd.setUsuario(u1);
		// Persist
		em.persist(pInd);

		//EMPRESA
		Empresa pEmp = new Empresa("RazSocial");
		List<CuentaFintech> pCuentasEmp = new ArrayList<CuentaFintech>();
		PersonaAutorizada pPAut = new PersonaAutorizada("Ident","Nacho", "Lopezosa", "54");
		Usuario u2 = new Usuario("USUARIO34-55","CLAVE-55",false);
		u2.setPersonaAutorizada(pPAut);
		pPAut.setUsuario(u2);
		//CUENTA INACTIVA
		Divisa pDiv2 = new Divisa("RUB", "rublo", 'R', (long)1);
		CuentaReferencia pRef2 = new CuentaReferencia("Imagin", (long)40000, pDiv2);
		Segregada pSeg2 = new Segregada("30",pRef2);
		//RELACION CON PERSONA AUTORIZADA
		Map<PersonaAutorizada,Character> pMapEMP = new HashMap<>();
		Map<Empresa,Character> pMapPAUT = new HashMap<>();

		pSeg2.setIban("NL66XYZW1291965208");
		pSeg2.setEstado("ACTIVA");
		pSeg2.setFechaApertura(pDay);
		pSeg2.setCliente(pEmp);
		pRef2.setIban("IBANREFXD12-55");

		//Persist
		em.persist(pDiv2);
		em.persist(pRef2);
		em.persist(pSeg2);
		em.persist(pEmp);
		em.persist(pPAut);
		//Setters & adds
		pEmp.setIdentificacion("Ide");
		pEmp.setTipo_cliente("EMPRESA");
		pEmp.setEstado("ACTIVO");
		pEmp.setFecha_Alta(pDay);
		pEmp.setDireccion("54");
		pEmp.setCiudad("Amsterdam");
		pEmp.setCodigoPostal("7207KE");
		pEmp.setPais("NL");
		pCuentasEmp.add(pSeg);
		pCuentasEmp.add(pSeg2);
		pEmp.setCuentas(pCuentasEmp);
		pEmp.setID(Long.valueOf(100001));
		pMapEMP.put(pPAut, 'A');
		pMapPAUT.put(pEmp, 'A');
		pEmp.setAutorizacion(pMapEMP);
		pPAut.setAutorizacion(pMapPAUT);

		// SetCuentas1()
		Divisa d1 = new Divisa("RAV", "raviolis", '%', (long)0.76);
		Divisa d2 = new Divisa("MAC", "macarrones", '#', (long)1.7);
		Divisa d3 = new Divisa("PES", "pesos", '$', (long)4.2);

		CuentaReferencia cR1 = new CuentaReferencia("BBVA-55", (long)40000, d1);
		CuentaReferencia cR2 = new CuentaReferencia("SANTANDER-55", (long)40000, d2);
		CuentaReferencia cR3 = new CuentaReferencia("CAJAMAR-55", (long)40000, d3);

		cR1.setIban("CUENTAREFLMAO-55");
		cR2.setIban("CUENTAREFLMAO1-55");
		cR3.setIban("CUENTAREFLMAO2-55");


		Segregada cS1 = new Segregada(cR1);
		Segregada cS2 = new Segregada(cR2);
		Segregada cS3 = new Segregada(cR3);

		cR1.setSegregada(cS1);
		cR2.setSegregada(cS2);
		cR3.setSegregada(cS3);

		Usuario u = new Usuario("USUARIO-55","CLAVE-55",false);

		Individual c1 = new Individual("Jesús","Cestino");

		List<CuentaFintech> list1 = new ArrayList<>();

		list1.add(cS1);
		list1.add(cS2);
		list1.add(cS3);

		Date pDate = new Date(119,6,23);

		u.setIndividual(c1);

		c1.setIdentificacion("CLIENTE1-55");
		c1.setTipo_cliente("INDIVIDUAL");
		c1.setEstado("ACTIVO");
		c1.setFecha_Alta(pDate);
		c1.setDireccion("53");
		c1.setCiudad("Sofía");
		c1.setCodigoPostal("29620");
		c1.setPais("germany");
		c1.setFechaNacimiento(pDate);
		c1.setID(Long.valueOf(555555));
		c1.setCuentas(list1);
		c1.setUsuario(u);
		
		c1ID = Long.valueOf(555555);

		//Setters segregadas
		cS1.setEstado("ACTIVA");
		cS2.setEstado("ACTIVA");
		cS3.setEstado("ACTIVA");

		cS1.setIban("ES1602091417-55");
		cS2.setIban("ES2602091417-55");
		cS3.setIban("ES3602091417-55");
		
		cS1IBAN = "ES1602091417-55";

		cS1.setFechaApertura(pDate);
		cS2.setFechaApertura(pDate);
		cS3.setFechaApertura(pDate);

		cS1.setCliente(c1);
		cS2.setCliente(c1);
		cS3.setCliente(c1);
		//Setters segregadas


		//Persist
		em.persist(c1);
		em.persist(d1);
		em.persist(d2);
		em.persist(d3);

		em.persist(cR1);
		em.persist(cR2);
		em.persist(cR3);

		em.persist(cS1);
		em.persist(cS2);
		em.persist(cS3);
		//Persist
		em.persist(pEmp);
		em.persist(pPAut);

		em.getTransaction().commit();

		em.close();
	}
//	public static void setCuentas1() {
//		EntityManager em = emf.createEntityManager();
//		em.getTransaction().begin();
//
//		// SetCuentas1()
//		Divisa d1 = new Divisa("RAV", "raviolis", '%', (long)0.76);
//		Divisa d2 = new Divisa("MAC", "macarrones", '#', (long)1.7);
//		Divisa d3 = new Divisa("PES", "pesos", '$', (long)4.2);
//
//		CuentaReferencia cR1 = new CuentaReferencia("BBVA-55", (long)40000, d1);
//		CuentaReferencia cR2 = new CuentaReferencia("SANTANDER-55", (long)40000, d2);
//		CuentaReferencia cR3 = new CuentaReferencia("CAJAMAR-55", (long)40000, d3);
//
//		cR1.setIban("CUENTAREFLMAO-55");
//		cR3.setIban("CUENTAREFLMAO2-55");
//		cR2.setIban("CUENTAREFLMAO1-55");
//
//
//		Segregada cS1 = new Segregada(cR1);
//		Segregada cS2 = new Segregada(cR2);
//		Segregada cS3 = new Segregada(cR3);
//
//		cR1.setSegregada(cS1);
//		cR2.setSegregada(cS2);
//		cR3.setSegregada(cS3);
//
//		Usuario u = new Usuario("USUARIO-55","CLAVE-55",false);
//
//		Individual c1 = new Individual("Jesús","Cestino");
//
//		List<CuentaFintech> list1 = new ArrayList<>();
//
//		list1.add(cS1);
//		list1.add(cS2);
//		list1.add(cS3);
//
//		Date pDate = new Date(119,6,23);
//
//		u.setIndividual(c1);
//
//		c1.setIdentificacion("CLIENTE1-55");
//		c1.setTipo_cliente("INDIVIDUAL");
//		c1.setEstado("ACTIVO");
//		c1.setFecha_Alta(pDate);
//		c1.setDireccion("53");
//		c1.setCiudad("Sofía");
//		c1.setCodigoPostal("29620");
//		c1.setPais("germany");
//		c1.setFechaNacimiento(pDate);
//		c1.setID((long)555555);
//		c1.setCuentas(list1);
//		c1.setUsuario(u);
//
//
//
//		//Setters segregadas
//		cS1.setEstado("ACTIVA");
//		cS2.setEstado("ACTIVA");
//		cS3.setEstado("ACTIVA");
//
//		cS1.setIban("ES1602091417-55");
//		cS2.setIban("ES2602091417-55");
//		cS3.setIban("ES3602091417-55");
//
//		cS1.setFechaApertura(pDate);
//		cS2.setFechaApertura(pDate);
//		cS3.setFechaApertura(pDate);
//
//		cS1.setCliente(c1);
//		cS2.setCliente(c1);
//		cS3.setCliente(c1);
//		//Setters segregadas
//
//
//		//Persist
//		em.persist(c1);
//		em.persist(d1);
//		em.persist(d2);
//		em.persist(d3);
//
//		em.persist(cR1);
//		em.persist(cR2);
//		em.persist(cR3);
//
//		em.persist(cS1);
//		em.persist(cS2);
//		em.persist(cS3);
//
//		em.getTransaction().commit();
//		em.close();
//
//	}

	public static void setCuentas2(){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Segregada cS1 = em.find(Segregada.class, cS1IBAN);
		
		//Set 45 para comprobar el fallo de la longitud del iban
		cS1.setIban("45");
		// Set a inactiva para comprobar el fallo en ejb periodico
		cS1.setEstado("INACTIVA");

		em.persist(cS1);

		em.getTransaction().commit();
		em.close();
	}
	public static void setCuentas3(){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Individual c1 = em.find(Individual.class, c1ID);
		Segregada cS1 = em.find(Segregada.class, cS1IBAN);
		
		c1.setFechaNacimiento(null);
		cS1.setEstado("ACTIVA");
		em.persist(c1);
		em.persist(cS1);

		em.getTransaction().commit();
		em.close();
		emf.close();
	}



}
