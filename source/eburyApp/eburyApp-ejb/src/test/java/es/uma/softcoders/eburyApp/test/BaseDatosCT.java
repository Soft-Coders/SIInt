package es.uma.softcoders.eburyApp.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.softcoders.eburyApp.Cliente;
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
		crPrueba.setIban("EresMuyTonto");
		em.persist(crPrueba);
		
		//Relación Cuentas Referencia, Pooled
		Map<CuentaReferencia,Long> mAuxiliar = new HashMap<>();
		mAuxiliar.put(crPrueba, (long)1);
		
		Cliente pCliente = new Cliente("iden", "tonto", "ACTIVO", fAuxiliar, "C/ Bobo, 4", "Boboville", "42069", "Tontokistan");
		em.persist(pCliente);
		
		//Pooled
		Pooled pPrueba = new Pooled(mAuxiliar);
		pPrueba.setIban("EresMasTontoAun");
		pPrueba.setEstado("ACTIVO");
		pPrueba.setFechaApertura(fAuxiliar);
		pPrueba.setSwift("MiSwiftDePrueba");
		pPrueba.setCliente(pCliente);
		em.persist(pPrueba);
		

		
		//Cierre Contexto Persistencia
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
