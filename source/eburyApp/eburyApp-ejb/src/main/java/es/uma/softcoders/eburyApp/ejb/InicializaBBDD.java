package es.uma.softcoders.eburyApp.ejb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	public void inicializar() {
		
		if(em.find(Segregada.class,"NL63ABNA6548268733") != null)
			return;
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-mm-dds");
		
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
		
		empresaP33.setIdentificacion("P3310693A");
		empresaP33.setTipo_cliente("EMPRESA");
		empresaP33.setFecha_Alta(new Date());
		empresaP33.setCiudad("Málaga");
		empresaP33.setCodigoPostal("29010");
		empresaP33.setPais("España");
		empresaP33.setRazonSocial("SA");
		
		individual639.setIdentificacion("63937528N");
		individual639.setTipo_cliente("INDIVIDUAL");
		individual639.setFecha_Alta(new Date());
		individual639.setCiudad("Madrid");
		individual639.setCodigoPostal("28770");
		individual639.setPais("España");
		individual639.setNombre("Oscar");
		individual639.setApellido("Lopezosa");
		
		Map<Empresa, Character> aut = new HashMap<>();
		aut.put(empresaP33, 'W');
		paY40.setIdentificacion("Y4001267V");
		paY40.setAutorizacion(aut);
		paY40.setNombre("Paco");
		paY40.setApellidos("Gutierrez");
		paY40.setDireccion("La calle de Paco");
		
		eur.setAbreviatura("EUR");
		eur.setNombre("EURO");
		eur.setCambioEuro(1.00);
		
		usd.setAbreviatura("USD");
		usd.setNombre("DOLAR");
		usd.setCambioEuro(0.95);
		
		gbp.setAbreviatura("GBP");
		gbp.setNombre("LIBRA");
		gbp.setCambioEuro(1.18);
		
		referenciaES7.setIban("ES7121007487367264321882");
		referenciaES7.setSaldo(1000.00);
		referenciaES7.setDivisa(eur);
		
		referenciaVG8.setIban("VG88HBIJ4257959912673134");
		referenciaVG8.setSaldo(1000.00);
		referenciaVG8.setDivisa(usd);
		
		referenciaGB7.setIban("GB79BARC20040134265953");
		referenciaGB7.setSaldo(1000.00);
		referenciaGB7.setDivisa(gbp);
		
		referenciaVG5.setIban("VG57DDVS5173214964983931");
		referenciaVG5.setSaldo(1000.00);
		referenciaVG5.setDivisa(usd);
		
		referenciaHN4.setIban("HN47QUXH11325678769785549996");
		referenciaHN4.setSaldo(1000.00);
		referenciaHN4.setDivisa(usd);
		
		segregadaNL.setIban("NL63ABNA6548268733");
		segregadaNL.setCliente(empresaP33);
		segregadaNL.setEstado("ACTIVO");
		segregadaNL.setCuentaRef(referenciaVG5);
		segregadaNL.setFechaApertura(new Date());
		
		segregadaFR.setIban("FR5514508000502273293129K55");
		segregadaFR.setCliente(empresaP33);
		segregadaFR.setEstado("ACTIVO");
		segregadaFR.setCuentaRef(referenciaHN4);
		segregadaFR.setFechaApertura(new Date());
		
		try {			
			segregadaDE.setIban("DE31500105179261215675");
			segregadaDE.setCliente(empresaP33);
			segregadaDE.setEstado("INACTIVO");
			segregadaDE.setFechaCierre(date.parse("2021-09-01"));
			segregadaDE.setFechaApertura(date.parse("2020-09-01"));
			segregadaDE.setCuentaRef(referenciaHN4);
		} catch(ParseException e) {
			throw new RuntimeException("Date ParseException with segregadaDE in InicializaBBDD");
		}
		
		Map<CuentaReferencia, Double> referenciasPooledES = new HashMap<>();
		referenciasPooledES.put(referenciaES7, 100.00);
		referenciasPooledES.put(referenciaVG8, 200.00);
		referenciasPooledES.put(referenciaGB7, 134.00);
		pooledES.setIban("ES8400817251647192321264");
		pooledES.setCliente(individual639);
		pooledES.setDepositadaEn(referenciasPooledES);
		
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
		
		usuarioJuan.setUsuario("juan");
		usuarioJuan.setClave("juan");
		usuarioJuan.setEsAdministrativo(false);
		usuarioJuan.setIndividual(individual639);
		
		usuarioAna.setUsuario("ana");
		usuarioAna.setClave("ana");
		usuarioAna.setEsAdministrativo(false);
		usuarioAna.setPersonaAutorizada(paY40);
		
		usuarioPonciano.setUsuario("ponciano");
		usuarioPonciano.setClave("ponciano");
		usuarioPonciano.setEsAdministrativo(true);
		
		for(Object o : new Object[] { empresaP33, individual639, paY40, segregadaNL, segregadaFR, segregadaDE, pooledES, 
										transaccionNL_ES, eur, usd, gbp, usuarioJuan, usuarioAna, usuarioPonciano, referenciaHN4, 
										referenciaES7, referenciaVG5, referenciaGB7, referenciaVG8})
			em.persist(o);
		
	}
	
}
