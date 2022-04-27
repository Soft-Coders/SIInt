package es.uma.softcoders.eburyApp.test;

import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.softcoders.eburyApp.CuentaReferencia;
import es.uma.softcoders.eburyApp.Divisa;
import es.uma.softcoders.eburyApp.Pooled;
import es.uma.softcoders.eburyApp.Transaccion;

public class BaseDatosCT {
	public static void inicializaBaseDatos(String nombreUnidadPersistencia) {
		//Inicio contexto de persistencia
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		//Variables auxiliares
		Date fAuxiliar = new Date(1,30,1989);
		
		//Divisas
		Divisa dLibra = new Divisa("GBP", "libras", '£', (long)1.18);
		em.persist(dLibra);
		Divisa dEuro = new Divisa("EUR", "euros", '€', (long)1);
		em.persist(dEuro);
		
		//Cuenta Referencia
		CuentaReferencia crPrueba = new CuentaReferencia("Santander", "Madrid", "España", (long)1000, fAuxiliar, "ACTIVA", null, dEuro);
		em.persist(crPrueba);
		
		//Relación Cuentas Referencia, Pooled
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
