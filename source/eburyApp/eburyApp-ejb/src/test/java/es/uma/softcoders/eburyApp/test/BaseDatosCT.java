package es.uma.softcoders.eburyApp.test;

import java.sql.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.softcoders.eburyApp.CuentaReferencia;
import es.uma.softcoders.eburyApp.Divisa;
import es.uma.softcoders.eburyApp.Pooled;
import es.uma.softcoders.eburyApp.Segregada;
import es.uma.softcoders.eburyApp.Transaccion;

public class BaseDatosCT {
	public static void inicializaBaseDatos(String nombreUnidadPersistencia) {
		//Inicio contexto de persistencia
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		//Divisas
		Divisa dLibra = new Divisa("GBP", "libras", '£', (long)1.18);
		Divisa dDolar = new Divisa("GBP", "libras", '£', (long)0.94);
		Divisa dEuro = new Divisa("EUR", "euros", 'Ä', (long)1);
		em.persist(dLibra);
		em.persist(dDolar);
		em.persist(dEuro);

		//Cuenta Segregada
		Segregada sPrueba = new Segregada();		
		//Cuenta Referencia
		CuentaReferencia crPrueba = new CuentaReferencia("Santander", "Madrid", "Espa√±a", (long)1000, Date.valueOf("1987-04-11"), "ACTIVA", null, dLibra);
		em.persist(crPrueba);
		
		//Relaci√≥n Cuentas Referencia, Pooled
		Map<CuentaReferencia,Long> mAuxiliar = null;
		mAuxiliar.put(crPrueba, (long)1);
		
		//Pooled
		Pooled pPrueba = new Pooled(mAuxiliar);
		em.persist(pPrueba);
		

		
		//Cierre Contexto Persistencia
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
