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
		Cliente cliente = new Cliente("0000", "tipo", "ACTIVO", date.parse("2019-04-05"),"Calle calle, 1", "ciudad", "29620", "pais");
		cliente.setID(Long.valueOf(0122));
		Pooled cpPooled = new Pooled();
		Usuario uPrueba = new Usuario();
		
		//Usuario
		uPrueba.setId(1111L);
		uPrueba.setUsuario("uPrueba");
		uPrueba.setClave("1234");
		uPrueba.setEsAdministrativo(false);
		
		//Cuenta Pooled
		cpPooled.setIban("cpPooled");
		cpPooled.setSwift("Swift");
		cpPooled.setEstado("ACTIVO");
		cpPooled.setFechaApertura(date.parse("2018-05-27"));
		cpPooled.setCliente(cliente);
		
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
		em.persist(cliente);
		em.persist(cpPooled);
		em.persist(dDolar);
		em.persist(dEuro);
		em.persist(cfPruebaD);
		em.persist(cfPruebaE);
		

		
		/*try {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			//----CuentaEJB
			CuentaFintech cfPreExistente = new CuentaFintech("ACTIVO", date.parse("2011-11-11"));
			cfPreExistente.setIban("cfPreExistente");
			
			Cliente cClienteExistente = new Cliente("0000", "tipo", "ACTIVO", date.parse("2020-02-22"), "Calle calle, 1", "ciudad", "29620", "pais");
			cClienteExistente.setID(Long.valueOf(0));
			
			CuentaFintech cfCuentaInactiva = new Segregada();
			cfCuentaInactiva.setIban("cfCuentaInactiva");
			cfCuentaInactiva.setCliente(cClienteExistente);
			cfCuentaInactiva.setEstado("INACTIVO");
			cfCuentaInactiva.setFechaApertura(date.parse("2021-12-12"));
			cfCuentaInactiva.setFechaCierre(date.parse("2022-02-02"));
			cfCuentaInactiva.setSwift("Swift");
			
			CuentaFintech cfIdealPooled = new Pooled();
			cfIdealPooled.setCliente(cClienteExistente);
			cfIdealPooled.setEstado("ACTIVO");
			cfIdealPooled.setFechaApertura(date.parse("2019-09-19"));
			cfIdealPooled.setIban("cfIdealPooled");
			cfIdealPooled.setSwift("Swift");
			
			CuentaFintech cfIdealSegregada = new Segregada();
			cfIdealSegregada.setCliente(cClienteExistente);
			cfIdealSegregada.setEstado("ACTIVO");
			cfIdealSegregada.setFechaApertura(date.parse("2019-09-19"));
			cfIdealSegregada.setIban("cfIdealSegregada");
			cfIdealSegregada.setSwift("Swift");
			

			
			for(CuentaFintech cf : new CuentaFintech [] {cfPreExistente, cfCuentaInactiva})
				em.persist(cf);
			
		}catch(ParseException e) {
			System.out.println("Problema con date.parse()");
		}
		
	
		//Divisas
		Divisa dLibra = new Divisa("GBP", "libras", '�', (long)1.18);
		Divisa dDolar = new Divisa("GBP", "libras", '�', (long)0.94);
		Divisa dEuro = new Divisa("EUR", "euros", '�', (long)1);
		em.persist(dLibra);
		em.persist(dDolar);
		em.persist(dEuro);

		//Cuenta Segregada
		Segregada sPrueba = new Segregada();		
		sPrueba.setIban("123456");
		
		//Cuenta Referencia
		SimpleDateFormat p = new SimpleDateFormat("yyyy-mm-dd");


		CuentaReferencia crPrueba = new CuentaReferencia("Santander", "Madrid", "España", Long.valueOf(1000), p.parse("1989-04-30"), "ACTIVA", sPrueba, dEuro);
		crPrueba.setIban("EresMuyTonto");
		em.persist(crPrueba);
		
		//Relación Cuentas Referencia, Pooled
		Map<CuentaReferencia,Long> mAuxiliar = new HashMap<>();
		mAuxiliar.put(crPrueba, (long)1);
		
		Cliente pCliente = new Cliente("iden", "tonto", "ACTIVO",p.parse("1989-02-22"), "C/ Bobo, 4", "Boboville", "42069", "Tontokistan");
		em.persist(pCliente);
		
		//Pooled
		Pooled pPrueba = new Pooled(mAuxiliar);
		pPrueba.setIban("EresMasTontoAun");
		pPrueba.setEstado("ACTIVO");
		pPrueba.setFechaApertura(p.parse("2022-04-29"));
		pPrueba.setSwift("MiSwiftDePrueba");
		pPrueba.setCliente(pCliente);
		em.persist(pPrueba);
		*/

		
		//Cierre Contexto Persistencia
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
