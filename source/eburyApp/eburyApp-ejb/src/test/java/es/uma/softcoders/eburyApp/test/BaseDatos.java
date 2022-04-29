package es.uma.softcoders.eburyApp.test;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;

public class BaseDatosAutorizado {
	public static void inicializaBaseDatos(String nombreUnidadPersistencia) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		//------------------------------------------------------
		
		//Personas Autorizadas
		PersonaAutorizada pa1 = new PersonaAutorizada();
		PersonaAutorizada pa2 = new PersonaAutorizada("ABC123", "Marta", "Maleno", "Calle Patata, 37");
		@SuppressWarnings("deprecation")
		Date cumple = new Date(2002,4,30);
		PersonaAutorizada pa3 = new PersonaAutorizada("ABC456", "Pablo", "Huertas", "Calle Boniato, 8", "ACTIVO",  cumple, new Date(), null);
		PersonaAutorizada pa4 = new PersonaAutorizada();
		PersonaAutorizada pa5 = new PersonaAutorizada("ABC789", "Ignacio", "Lopezosa", "Calle Cebolla, 13");
		PersonaAutorizada pa6 = new PersonaAutorizada("DEF123", "Jesús", "Cestino", "Calle Puerro, 16");
		
		for (PersonaAutorizada personaAutorizada: new PersonaAutorizada [] {pa1, pa2, pa3, pa4, pa5, pa6}) {
			em.persist(personaAutorizada);
		}
		
		//Clientes
		Cliente cl1 = new Cliente();
		Cliente cl2 = new Cliente();
		Cliente cl3 = new Cliente("ignacioLop", "Individual", "Activo", new Date(), "Calle dnjisak, 33", "Málaga", "29011", "España");
		Cliente cl4 = new Cliente("jesusCest", "Individual", "Activo", new Date(), "Calle ahnskc, 90", "Málaga", "29004", "España");
		
		for (Cliente cliente: new Cliente [] {cl1, cl2, cl3, cl4}) {
			em.persist(cliente);
		}
		
		//Empresas
		Empresa em1 = new Empresa();
		Empresa em2 = new Empresa();
		Empresa em3 = new Empresa("Aldi");
		Empresa em4 = new Empresa("IKEA");
		
		for (Empresa empresa: new Empresa [] {em1, em2, em3, em4}) {
			em.persist(empresa);
		}
		
		//Individuales
		Individual in1 = new Individual("Manuel", "González");
		Individual in2 = new Individual("Lucía", "Ferre");
		Individual in3 = new Individual("Pedro", "Piqueras");
		Individual in4 = new Individual("Jacinto", "Benavente");

		
		
		for (Individual individual: new Individual [] {in1, in2, in3, in4}) {
			em.persist(individual);
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
		//Cuenta Referencia
		CuentaReferencia crPrueba = new CuentaReferencia("Santander", "Madrid", "España", (long)1000, Date.valueOf("1987-04-11"), "ACTIVA", null, dLibra);
		em.persist(crPrueba);
		
		//Relación Cuentas Referencia, Pooled
		Map<CuentaReferencia,Long> mAuxiliar = new HashMap<>();
		mAuxiliar.put(crPrueba, (long)1);
				
		Cliente pCliente = new Cliente("iden", "tonto", "ACTIVO", fAuxiliar, "C/ Bobo, 4", "Boboville", 42069, "Tontokistan");
		em.persist(pCliente);
				
		//Pooled
		Pooled pPrueba = new Pooled(mAuxiliar);
		pPrueba.setIban("EresMasTontoAun");
		pPrueba.setEstado("ACTIVO");
		pPrueba.setFechaApertura(fAuxiliar);
		pPrueba.setSwift("MiSwiftDePrueba");
		pPrueba.setCliente(pCliente);
		em.persist(pPrueba);
		
		//------------------------------------------------------
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
