package es.uma.softcoders.eburyApp.test;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.Segregada;

public class BaseDatosCuentas {
	public static void inicializaBaseDatos(String nombreUnidadPersistencia) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		
		CuentaFintech cfPreExistente = new CuentaFintech("ACTIVO", Date.valueOf("2020-04-13"));
		cfPreExistente.setIBAN("cfPreExistente");
		CuentaFintech cfIbanNulo = new CuentaFintech("ACTIVO", Date.valueOf("2018-10-24"));
		
		
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
