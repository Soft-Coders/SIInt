package es.uma.softcoders.eburyApp.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.CuentaFintech;
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
			CuentaFintech cfPreExistente = new CuentaFintech("ACTIVO", date.parse("2011-11-11"));
			cfPreExistente.setIBAN("cfPreExistente");
			
			Cliente cClienteExistente = new Cliente("0000", "tipo", "ACTIVO", date.parse("2020-02-22"), "Calle calle, 1", "ciudad", "29620", "pais");
			cClienteExistente.setID(Long.valueOf(0));
			
			CuentaFintech cfCuentaInactiva = new Segregada();
			cfCuentaInactiva.setIBAN("cfCuentaInactiva");
			cfCuentaInactiva.setCliente(cClienteExistente);
			cfCuentaInactiva.setEstado("INACTIVO");
			cfCuentaInactiva.setFechaApertura(date.parse("2021-12-12"));
			cfCuentaInactiva.setFechaCierre(date.parse("2022-02-02"));
			cfCuentaInactiva.setSwift("Swift");
			
			CuentaFintech cfIdealPooled = new Pooled();
			cfIdealPooled.setCliente(cClienteExistente);
			cfIdealPooled.setEstado("ACTIVO");
			cfIdealPooled.setFechaApertura(date.parse("2019-09-19"));
			cfIdealPooled.setIBAN("cfIdealPooled");
			cfIdealPooled.setSwift("Swift");
			
			CuentaFintech cfIdealSegregada = new Segregada();
			cfIdealSegregada.setCliente(cClienteExistente);
			cfIdealSegregada.setEstado("ACTIVO");
			cfIdealSegregada.setFechaApertura(date.parse("2019-09-19"));
			cfIdealSegregada.setIBAN("cfIdealSegregada");
			cfIdealSegregada.setSwift("Swift");
			
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
