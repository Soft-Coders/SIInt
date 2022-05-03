package es.uma.softcoders.eburyApp.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class BaseDatosCT {
	public static void inicializaBaseDatos(String nombreUnidadPersistencia) throws ParseException {
		//Inicio contexto de persistencia
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		try {
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
		

		
		//Cierre Contexto Persistencia
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
