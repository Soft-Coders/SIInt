package es.uma.softcoders.eburyApp.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.CuentaReferencia;
import es.uma.softcoders.eburyApp.Divisa;
import es.uma.softcoders.eburyApp.Pooled;
import es.uma.softcoders.eburyApp.Segregada;

public class BaseDatosCuenta {
	public static void inicializaBaseDatos(String nombreUnidadPersistencia) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		try {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			//----CuentaEJB
			Cliente cClienteExistente = new Cliente("0000", "tipo", "ACTIVO", date.parse("2020-02-22"), "Calle calle, 1", "ciudad", "29620", "pais");

			CuentaFintech cfPreExistente = new CuentaFintech("ACTIVO", date.parse("2011-11-11"));
			cfPreExistente.setIBAN("cfPreExistente-22");
			cfPreExistente.setCliente(cClienteExistente);
			cfPreExistente.setEstado("ACTIVO");
			cfPreExistente.setFechaApertura(date.parse("2019-09-19"));
			
			CuentaFintech cfCuentaInactiva = new CuentaFintech();
			cfCuentaInactiva.setIBAN("cfCuentaInactiva-22");
			cfCuentaInactiva.setCliente(cClienteExistente);
			cfCuentaInactiva.setEstado("INACTIVO");
			cfCuentaInactiva.setFechaApertura(date.parse("2021-12-12"));
			cfCuentaInactiva.setFechaCierre(date.parse("2022-02-02"));
			cfCuentaInactiva.setSwift("Swift");
			
			Divisa eur = new Divisa("EUR-22", "Euro", Long.valueOf(1));
			
			CuentaReferencia cr = new CuentaReferencia("nombreBanco", Long.valueOf(1000000), eur);
			cr.setIban("cr-22");
			
			HashMap<CuentaReferencia, Long> hMap = new HashMap<>();
			hMap.put(cr, cr.getSaldo());
			
			Pooled cfIdealPooled = new Pooled();
			cfIdealPooled.setCliente(cClienteExistente);
			cfIdealPooled.setEstado("ACTIVO");
			cfIdealPooled.setFechaApertura(date.parse("2019-09-19"));
			cfIdealPooled.setIBAN("cfIdealPooled-22");
			cfIdealPooled.setSwift("Swift");
			cfIdealPooled.setDepositadaEn(hMap);
			
			Segregada cfIdealSegregada = new Segregada();
			cfIdealSegregada.setCliente(cClienteExistente);
			cfIdealSegregada.setEstado("ACTIVO");
			cfIdealSegregada.setFechaApertura(date.parse("2019-09-19"));
			cfIdealSegregada.setIBAN("cfIdealSegregada-22");
			cfIdealSegregada.setSwift("Swift");
			cfIdealSegregada.setCuentaRef(cr);
			
	//		em.persist(cClienteExistente);
			
			for(CuentaFintech cf : new CuentaFintech [] {cfPreExistente, cfCuentaInactiva})
				em.persist(cf);
			
		}catch(ParseException e) {
			System.out.println("Problema con date.parse()");
		}
		
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
	
//	public static void dropBaseDatos(String nombreUnidadPersistencia) {
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
//		EntityManager em = emf.createEntityManager();
//		
//		em.getTransaction().begin();
//		
//		for(String cf : new String [] {"cfPreExistente", "cfCuentaInactiva"})
//			em.remove(em.find(CuentaFintech.class, cf));
//		
//		em.getTransaction().commit();
//		
//		em.close();
//		emf.close();
//	}
}
