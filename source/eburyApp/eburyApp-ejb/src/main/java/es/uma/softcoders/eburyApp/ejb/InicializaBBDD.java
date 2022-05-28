package es.uma.softcoders.eburyApp.ejb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.softcoders.eburyApp.Cuenta;
import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.CuentaReferencia;
import es.uma.softcoders.eburyApp.Divisa;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Pooled;
import es.uma.softcoders.eburyApp.Segregada;
import es.uma.softcoders.eburyApp.Transaccion;
import es.uma.softcoders.eburyApp.Usuario;

@Singleton
@Startup
public class InicializaBBDD {
	
	@PersistenceContext(unitName="eburyAppEjb")
	private EntityManager em;
	
	@PostConstruct
	public void inicializar() throws ParseException{
		
		Cuenta checkCuenta = em.find(Cuenta.class, "EUR");
		Individual checkIndividual = (checkCuenta == null) ? null :
			(Individual) em.createQuery("SELECT i FROM Individual i WHERE i.identifiacion LIKE '63937528N'").getSingleResult();
		PersonaAutorizada checkPA = (checkIndividual == null) ? null :
			(PersonaAutorizada) em.createQuery("SELECT pa FROM PersonaAutorizada pa WHERE pa.identificacion LIKE 'Y4001267V'").getSingleResult();
		if(checkPA != null)
			return;
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		
		Empresa empresaP33 = new Empresa();
		Individual individual639 = new Individual();
		PersonaAutorizada paY40 = new PersonaAutorizada();
		Segregada segregadaNL = new Segregada();
		Segregada segregadaFR = new Segregada();
		Segregada segregadaDE = new Segregada();
		Pooled pooledES = new Pooled();
		Transaccion transaccionNL_ES = new Transaccion();
		Divisa eur = new Divisa();
		Divisa usd = new Divisa();
		Divisa gbp = new Divisa();
		Usuario usuarioJuan = new Usuario();
		Usuario usuarioAna = new Usuario();
		Usuario usuarioPonciano = new Usuario();
		CuentaReferencia referenciaHN4 = new CuentaReferencia();
		CuentaReferencia referenciaES7 = new CuentaReferencia();
		CuentaReferencia referenciaVG5 = new CuentaReferencia();
		CuentaReferencia referenciaGB7 = new CuentaReferencia();
		CuentaReferencia referenciaVG8 = new CuentaReferencia();

		usuarioJuan.setUsuario("juan");
		usuarioJuan.setClave("juan");
		usuarioJuan.setEsAdministrativo(false);
		em.persist(usuarioJuan);
		
		usuarioAna.setUsuario("ana");
		usuarioAna.setClave("ana");
		usuarioAna.setEsAdministrativo(false);
		em.persist(usuarioAna);
		
		usuarioPonciano.setUsuario("ponciano");
		usuarioPonciano.setClave("ponciano");
		usuarioPonciano.setEsAdministrativo(true);
		em.persist(usuarioPonciano);
		
		List<CuentaFintech> cuentasP33 = new ArrayList<CuentaFintech>();
		cuentasP33.add(segregadaNL);
		cuentasP33.add(segregadaFR);
		cuentasP33.add(segregadaDE);
		empresaP33.setIdentificacion("P3310693A");
		empresaP33.setTipoCliente("EMPRESA");
		empresaP33.setFechaAlta(new Date());
		empresaP33.setEstado("ACTIVO");
		empresaP33.setDireccion("Direccion");
		empresaP33.setCiudad("Málaga");
		empresaP33.setCodigoPostal("29010");
		empresaP33.setPais("España");
		empresaP33.setRazonSocial("SA");
		empresaP33.setCuentas(cuentasP33);
		em.persist(empresaP33);
		
		List<CuentaFintech> cuentas639 = new ArrayList<CuentaFintech>();
		cuentas639.add(pooledES);
		individual639.setIdentificacion("63937528N");
		individual639.setTipoCliente("INDIVIDUAL");
		individual639.setFechaAlta(new Date());
		individual639.setEstado("ACTIVO");
		individual639.setDireccion("La calle de Juan");
		individual639.setCiudad("Madrid");
		individual639.setCodigoPostal("28770");
		individual639.setPais("España");
		individual639.setNombre("Juan");
		individual639.setApellido("");
		individual639.setUsuario(usuarioJuan);
		individual639.setCuentas(cuentas639);
		em.persist(individual639);
		
		Map<Empresa, Character> aut = new HashMap<>();
		aut.put(empresaP33, 'W');
		paY40.setIdentificacion("Y4001267V");
		paY40.setAutorizacion(aut);
		paY40.setNombre("Ana");
		paY40.setApellidos("");
		paY40.setDireccion("La calle de Ana");
		paY40.setUsuario(usuarioAna);
		paY40.setEstado("INACTIVO");
		paY40.setFechaInicio(new Date());
		em.persist(paY40);
		
		eur.setAbreviatura("EUR");
		eur.setNombre("EURO");
		eur.setCambioEuro(1.00);
		em.persist(eur);
		
		usd.setAbreviatura("USD");
		usd.setNombre("DOLAR");
		usd.setCambioEuro(0.95);
		em.persist(usd);
		
		gbp.setAbreviatura("GBP");
		gbp.setNombre("LIBRA");
		gbp.setCambioEuro(1.18);
		em.persist(gbp);
		
		referenciaES7.setIban("ES7121007487367264321882");
		referenciaES7.setSaldo(1000.00);
		referenciaES7.setDivisa(eur);
		referenciaES7.setNombreBanco("Santander");
		em.persist(referenciaES7);
		
		referenciaVG8.setIban("VG88HBIJ4257959912673134");
		referenciaVG8.setSaldo(1000.00);
		referenciaVG8.setDivisa(usd);
		referenciaVG8.setNombreBanco("HSBC");
		em.persist(referenciaVG8);
		
		referenciaGB7.setIban("GB79BARC20040134265953");
		referenciaGB7.setSaldo(1000.00);
		referenciaGB7.setDivisa(gbp);
		referenciaGB7.setNombreBanco("HSBC");
		em.persist(referenciaGB7);
		
		referenciaVG5.setIban("VG57DDVS5173214964983931");
		referenciaVG5.setSaldo(1000.00);
		referenciaVG5.setDivisa(usd);
		referenciaVG5.setNombreBanco("HSBC");
		em.persist(referenciaVG5);
		
		referenciaHN4.setIban("HN47QUXH11325678769785549996");
		referenciaHN4.setSaldo(1000.00);
		referenciaHN4.setDivisa(usd);
		referenciaHN4.setNombreBanco("Banco Central de Honduras");
		em.persist(referenciaHN4);
		
		segregadaNL.setIban("NL63ABNA6548268733");
		segregadaNL.setCliente(empresaP33);
		segregadaNL.setEstado("ACTIVO");
		segregadaNL.setCuentaRef(referenciaVG5);
		segregadaNL.setFechaApertura(new Date());
		em.persist(segregadaNL);
		
		segregadaFR.setIban("FR5514508000502273293129K55");
		segregadaFR.setCliente(empresaP33);
		segregadaFR.setEstado("ACTIVO");
		segregadaFR.setCuentaRef(referenciaHN4);
		segregadaFR.setFechaApertura(new Date());
		em.persist(segregadaFR);
		
		try {			
			segregadaDE.setIban("DE31500105179261215675");
			segregadaDE.setCliente(empresaP33);
			segregadaDE.setEstado("INACTIVO");
			segregadaDE.setFechaCierre(date.parse("2021-09-01"));
			segregadaDE.setFechaApertura(date.parse("2020-09-01"));
			segregadaDE.setCuentaRef(referenciaHN4);
			em.persist(segregadaDE);
		} catch(ParseException e) {
			System.out.println("Date ParseException with segregadaDE in InicializaBBDD");
		}
		
		Map<CuentaReferencia, Double> referenciasPooledES = new HashMap<>();
		referenciasPooledES.put(referenciaES7, 100.00);
		referenciasPooledES.put(referenciaVG8, 200.00);
		referenciasPooledES.put(referenciaGB7, 134.00);
		pooledES.setIban("ES8400817251647192321264");
		pooledES.setCliente(individual639);
		pooledES.setDepositadaEn(referenciasPooledES);
		pooledES.setEstado("ACTIVO");
		pooledES.setFechaApertura(new Date());
		em.persist(pooledES);
		
		// RF13 Y RF14 NO HAN SIDO CONTEMPLADOS EN ESTE PROYECTO
		// ¿CÓMO PROCEDER CON EL SIGUIENTE BLOQUE?  TODO
		transaccionNL_ES.setOrigen(segregadaNL);
		transaccionNL_ES.setDivisaEmisor(usd);
		transaccionNL_ES.setDestino(pooledES);
		transaccionNL_ES.setDivisaReceptor(usd);
		transaccionNL_ES.setCantidad(200.00);
		transaccionNL_ES.setFechaInstruccion(new Date());
		transaccionNL_ES.setFechaEjecucion(new Date());
		transaccionNL_ES.setTipo("TRANSFERENCIA");
		em.persist(transaccionNL_ES);
		

//		CuentaReferencia cfPruebaD = new CuentaReferencia();
//		CuentaReferencia cfPruebaE = new CuentaReferencia();
//		
//		Usuario uPruebaPA = new Usuario();
//		//Usuario
//		uPruebaPA.setId(2345L);
//		uPruebaPA.setUsuario("uPruebaPA");
//		uPruebaPA.setClave("1234");
//		uPruebaPA.setEsAdministrativo(false);
//	
//		
//		PersonaAutorizada paPrueba = new PersonaAutorizada();
//		paPrueba.setId(1234L);
//		paPrueba.setIdentificacion("1234");
//		paPrueba.setNombre("paPrueba");
//		paPrueba.setApellidos("prueba persona");
//		paPrueba.setDireccion("calle prueba");
//		paPrueba.setUsuario(uPruebaPA);
//		paPrueba.setEstado("ACTIVO");
//		uPruebaPA.setPersonaAutorizada(paPrueba);
//		
//		
//		//Cliente individual
//		Individual c = new Individual();
//		c.setID(Long.valueOf(0001));
//		c.setIdentificacion("0001");
//		c.setTipo_cliente("INDIVIDUAL");
//		c.setEstado("ACTIVO");
//		c.setFecha_Alta(date.parse("2022-05-12"));
//		c.setDireccion("Calle prueba, 32");
//		c.setCiudad("Malaga");
//		c.setCodigoPostal("29010");
//		c.setPais("Alemania");
//		c.setNombre("Cliente");
//		c.setApellido("Prueba");
//		c.setFechaNacimiento(date.parse("2002-30-04"));
//		
//		//Cliente judírico (empresa)
//		Empresa e = new Empresa();
//		e.setID(1111L);
//		e.setIdentificacion("1111");
//		e.setTipo_cliente("EMPRESA");
//		e.setEstado("BAJA");
//		e.setFecha_Alta(date.parse("2022-05-12"));
//		e.setDireccion("Calle prueba, 32");
//		e.setCiudad("Malaga");
//		e.setCodigoPostal("29010");
//		e.setPais("España");
//		e.setRazonSocial("Razon");
//		
//		Map<Empresa,Character> autorizacionE = new HashMap<>();
//		autorizacionE.put(e, 'L');
//		paPrueba.setAutorizacion(autorizacionE);	
//		
//		Pooled cpPooled = new Pooled();
//		Usuario uPrueba = new Usuario();
//		Usuario uPruebaAdmin = new Usuario();
//		
//		//Usuario
//		uPrueba.setId(1111L);
//		uPrueba.setUsuario("uPrueba");
//		uPrueba.setClave("1234");
//		uPrueba.setEsAdministrativo(false);
//		uPrueba.setIndividual(c);
//		c.setUsuario(uPrueba);
//		
//		uPruebaAdmin.setId(2222L);
//		uPruebaAdmin.setUsuario("uPruebaAdmin");
//		uPruebaAdmin.setClave("1234");
//		uPruebaAdmin.setEsAdministrativo(true);
//		
//		//Cuenta Pooled
//		cpPooled.setIban("cpPooled");
//		cpPooled.setSwift("Swift");
//		cpPooled.setEstado("ACTIVO");
//		cpPooled.setFechaApertura(date.parse("2018-05-27"));
//		cpPooled.setCliente(c);
//		
//		//Cuenta Segregada
//		Segregada cpSegregada = new Segregada();
//		cpSegregada.setIban("cpSegregada");
//		cpSegregada.setSwift("SwiftSegregada");
//		cpSegregada.setEstado("ACTIVO");
//		cpSegregada.setFechaApertura(date.parse("2019-06-28"));
//		cpSegregada.setCuentaRef(cfPruebaE);
//		cpSegregada.setCliente(c);
//		
//		//Cuenta Inactiva
//		Segregada cpInactiva = new Segregada();
//		cpInactiva.setIban("cpInactiva");
//		cpInactiva.setSwift("SwiftInactiva");
//		cpInactiva.setEstado("INACTIVO");
//		cpInactiva.setFechaApertura(date.parse("2019-06-29"));
//		cpInactiva.setFechaCierre(date.parse("2019-06-30"));
//		cpInactiva.setCuentaRef(cfPruebaD);
//		cpInactiva.setCliente(c);
//		
//		//Divisas
//		Divisa dDolar = new Divisa("DOL", "dolares", '$', 1.05);
//		Divisa dEuro = new Divisa("EUR", "euros", '€', 1.00);
//		
//		//Cuentas referencias
//		cfPruebaD.setIban("cfPruebaD");
//		cfPruebaD.setNombreBanco("Santander");
//		cfPruebaD.setSaldo(10000.0);
//		cfPruebaD.setFechaApertura(date.parse("2010-02-22"));
//		cfPruebaD.setEstado("ACTIVO");
//		cfPruebaD.setSwift("Swift");
//		cfPruebaD.setSucursal("Madrid");
//		cfPruebaD.setDivisa(dDolar);
//		
//		cfPruebaE.setIban("cfPruebaE");
//		cfPruebaE.setNombreBanco("Santander");
//		cfPruebaE.setSaldo(10000.0);
//		cfPruebaE.setFechaApertura(date.parse("2010-02-22"));
//		cfPruebaE.setEstado("ACTIVO");
//		cfPruebaE.setSwift("Swift");
//		cfPruebaE.setSucursal("Madrid");
//		cfPruebaE.setDivisa(dEuro);
//		
//		//Relación cuenta referencias - pooled
//		Map<CuentaReferencia,Double> depositado = new HashMap<>();
//		depositado.put(cfPruebaD, 5000.0);
//		depositado.put(cfPruebaE, 5000.0);
//		
//		cpPooled.setDepositadaEn(depositado);
//		
//		//Introducción en la base de datos
//		em.persist(e);
//		em.persist(uPrueba);
//		em.persist(uPruebaAdmin);
//		em.persist(c);
//		em.persist(cpPooled);
//		em.persist(cpSegregada);
//		em.persist(cpInactiva);
//		em.persist(dDolar);
//		em.persist(dEuro);
//		em.persist(cfPruebaD);
//		em.persist(cfPruebaE);
//		em.persist(paPrueba);
//		em.persist(uPruebaPA);
//		
//		//Personas Autorizadas
//		PersonaAutorizada pa1 = new PersonaAutorizada("ABC123", "Marta", "Maleno", "Calle Patata, 37");
//		PersonaAutorizada pa2 = new PersonaAutorizada("ABC456", "Pablo", "Huertas", "Calle Boniato, 8");
//		pa1.setId((long)123000);
//		pa2.setId((long)456000);
//		Usuario u1 = new Usuario("USUARIO1", "clave1", true);
//		Usuario u2 = new Usuario("USUARIO2", "clave2", false);
//		pa1.setUsuario(u1);
//		pa2.setUsuario(u2);
//		u1.setPersonaAutorizada(pa1);
//		u2.setPersonaAutorizada(pa2);
//		em.persist(pa1);
//		em.persist(pa2);
//		em.persist(u1);
//		em.persist(u2);
//		
//		//Empresas
//		Empresa em1 = new Empresa("Empresa1");
//		Empresa em2 = new Empresa("Empresa2");
//		em1.setID((long)123);
//		em2.setID((long)456);
//		em1.setIdentificacion("Empresa1");
//		em2.setIdentificacion("Empresa2");
//		em1.setTipo_cliente("EMPRESA");
//		em2.setTipo_cliente("EMPRESA");
//		em1.setEstado("Activo");
//		em1.setFecha_Alta(new Date());
//		em1.setDireccion("Calle calle, 0");
//		em1.setCiudad("Málaga");
//		em1.setCodigoPostal("29000");
//		em1.setPais("Esp");
//		em2.setEstado("Activo");
//		em2.setFecha_Alta(new Date());
//		em2.setDireccion("Calle calle, 0");
//		em2.setCiudad("Málaga");
//		em2.setCodigoPostal("29000");
//		em2.setPais("Esp");
//		em.persist(em1);
//		em.persist(em2);
	}
	
}
